package carry.ilearn.service.impl;

import carry.ilearn.common.error.BusinessException;
import carry.ilearn.common.error.EmBusinessError;
import carry.ilearn.converter.CourseConverter;
import carry.ilearn.dao.CourseDao;
import carry.ilearn.dao.CourseReferDao;
import carry.ilearn.dao.CoursewareGroupDao;
import carry.ilearn.dataobject.CourseDO;
import carry.ilearn.dataobject.CourseReferDO;
import carry.ilearn.dataobject.CoursewareGroupDO;
import carry.ilearn.model.PageModel;
import carry.ilearn.query.*;
import carry.ilearn.service.CourseService;
import carry.ilearn.service.datatransferobject.CourseDTO;
import carry.ilearn.service.datatransferobject.CourseReferDTO;
import carry.ilearn.service.utils.CourseLearnServiceUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Carry
 * @since 2021/5/17
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class CourseServiceImpl implements CourseService {
    @Resource
    private CourseDao courseDao;
    @Resource
    private CourseConverter courseConverter;
    @Resource
    private CourseLearnServiceUtil courseLearnServiceUtil;
    @Resource
    private CoursewareGroupDao coursewareGroupDao;
    @Resource
    private CourseReferDao courseReferDao;
    
    @Override
    public void addCourse(CourseCreationQuery courseCreationQuery) {
        CourseDO courseDO = courseConverter.courseCreationQuery2CourseDO(courseCreationQuery);
        courseDao.insertSelective(courseDO);
        final CoursewareGroupDO coursewareGroupDO = new CoursewareGroupDO();
        coursewareGroupDO.setCourseId(courseDO.getId());
        coursewareGroupDao.insertSelective(coursewareGroupDO);
    }
    
    @Override
    public void banCourse(CourseIdentifierQuery courseIdentifierQuery) throws BusinessException {
        final int i = courseDao.updateBannedByIdAndUserId(courseIdentifierQuery.getCourseId(),
                                                          courseIdentifierQuery.getUserId(),
                                                          courseIdentifierQuery.getBanned());
        if (i == 0) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "未找到该课程");
        }
    }
    
    @Override
    public List<CourseDTO> getCourseListByUser(Integer userId) {
        final List<CourseDO> courseDOs = courseDao.selectByUserId(userId);
        final List<CourseDTO> courseDTOS = courseConverter.listCourseDO2CourseDTO(courseDOs);
        return courseDTOS;
    }
    
    @Override
    public CourseDTO getCourseById(Integer courseId) throws BusinessException {
        final CourseDO courseDO = courseLearnServiceUtil.getCourseWithBannedCheck(courseId);
        return courseConverter.courseDO2CourseDTO(courseDO);
    }
    
    @Override
    public void updateCourseOverview(CourseOverviewQuery courseOverviewQuery) throws BusinessException {
        final CourseDO courseDOOrigin =
                courseLearnServiceUtil.checkCourseOwnerAndGetOrigin(courseOverviewQuery.getUserId(),
                                                                    courseOverviewQuery.getId());
        courseDOOrigin.setTitle(courseOverviewQuery.getTitle());
        courseDOOrigin.setNotice(courseOverviewQuery.getNotice());
        courseDao.updateByPrimaryKeySelective(courseDOOrigin);
    }
    
    @Override
    public void updateCoursePreviewImgUrl(CoursePreviewImgUrlQuery coursePreviewImgUrlQuery) throws BusinessException {
        final CourseDO courseDOOrigin =
                courseLearnServiceUtil.checkCourseOwnerAndGetOrigin(coursePreviewImgUrlQuery.getUserId(),
                                                                    coursePreviewImgUrlQuery.getId());
        courseDOOrigin.setPreviewImgUrl(coursePreviewImgUrlQuery.getUrl());
        courseDao.updateByPrimaryKeySelective(courseDOOrigin);
    }
    
    @Override
    public PageModel<CourseDTO> getCourseListPaged(PageQuery pageQuery) {
        List<CourseDO> courseDOs =
                courseDao.selectAllPaged(pageQuery.getPageIdx() * pageQuery.getPageSize(), pageQuery.getPageSize());
        final List<CourseDTO> courseDTOS = courseConverter.listCourseDO2CourseDTO(courseDOs);
        
        final long totalNum = courseDao.selectTotalNum();
        
        final PageModel<CourseDTO> pageModel = new PageModel<>();
        pageModel.setTotalNum(totalNum);
        pageModel.setData(courseDTOS);
        return pageModel;
    }
    
    @Override
    public void addRefer(CourseReferQuery courseReferQuery) throws BusinessException {
        courseLearnServiceUtil.checkCourseOwner(courseReferQuery.getUserId(), courseReferQuery.getCourseId());
        final CourseReferDO courseReferDO = new CourseReferDO();
        courseReferDO.setCourseId(courseReferQuery.getCourseId());
        courseReferDO.setTitle(courseReferQuery.getTitle());
        courseReferDO.setHref(courseReferQuery.getHref());
        courseReferDao.insertSelective(courseReferDO);
    }
    
    @Override
    public List<CourseReferDTO> getRefersByCourseId(Integer courseId) {
        final List<CourseReferDO> courseReferDOS = courseReferDao.selectByCourseId(courseId);
        final List<CourseReferDTO> courseReferDTOS = courseConverter.listCourseReferDO2CourseReferDTO(courseReferDOS);
        return courseReferDTOS;
    }
    
    @Override
    public void deleteRefer(CourseReferDeleteQuery courseReferDeleteQuery) {
        courseReferDao.deleteByUser(courseReferDeleteQuery.getReferId(), courseReferDeleteQuery.getUserId());
    }
    
}
