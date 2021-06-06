package carry.ilearn.service.utils;

import carry.ilearn.common.error.BusinessException;
import carry.ilearn.common.error.EmBusinessError;
import carry.ilearn.dao.ChapterDao;
import carry.ilearn.dao.CourseDao;
import carry.ilearn.dataobject.CourseDO;
import carry.ilearn.service.UserStateService;
import carry.ilearn.service.datatransferobject.UserDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author Carry
 * @since 2021/5/19
 */
@Component
public class CourseLearnServiceUtil {
    @Resource
    private CourseDao courseDao;
    @Resource
    private UserStateService userStateService;
    @Resource
    private ChapterDao chapterDao;
    
    public void checkCourseOwner(Integer userId, Integer courseId) throws BusinessException {
        final boolean exists = courseDao.checkByIdAndUserId(courseId, userId);
        if (!exists) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户校验失败");
        }
    }
    
    public CourseDO checkCourseOwnerAndGetOrigin(Integer userId, Integer courseId) throws BusinessException {
        final CourseDO courseDOOrigin = courseDao.selectByIdAndUserId(courseId, userId);
        if (courseDOOrigin == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户校验失败");
        }
        return courseDOOrigin;
    }
    
    public CourseDO getCourseWithBannedCheck(Integer courseId) throws BusinessException {
        final CourseDO courseDO = courseDao.selectByPrimaryKey(courseId);
        if (courseDO == null) {
            // todo 前端跳转
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "课程不存在");
        }
        if (courseDO.getBanned()) {
            final UserDTO loginUser = userStateService.getLoginUser();
            if (loginUser == null || !Objects.equals(loginUser.getId(), courseDO.getUserId())) {
                // todo 前端跳转
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "该课程已禁用");
            }
        }
        return courseDO;
    }
    
    public Integer checkChapterOwner(Integer userId, Integer chapterId) throws BusinessException {
        final Integer courseId = chapterDao.checkByIdAndUserId(chapterId, userId);
        if (courseId == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户校验失败");
        }
        return courseId;
    }
    
}
