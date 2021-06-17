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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Carry
 * @since 2021/5/17
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class CourseServiceImpl implements CourseService, InitializingBean {
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
    @Resource
    private RedisTemplate<String, String> redisTemplateCourseKeys;
    @Resource
    private RedisTemplate<String, CourseDTO> redisTemplateCourse;
    
    @Override
    public void afterPropertiesSet() {
        final int page = 8;
        for (int offset = 0; ; offset += page) {
            final List<CourseDO> courseDOS = courseDao.selectAllPaged(offset, page);
            final Set<TypedTuple<String>> collect = courseDOS.stream()
                                                             .filter(c -> !c.getBanned())
                                                             .map(c -> new DefaultTypedTuple<>(
                                                                     "course_of_id_" + c.getId(),
                                                                     c.getId().doubleValue()))
                                                             .collect(Collectors.toSet());
            redisTemplateCourseKeys.opsForZSet().add("course_available_keys", collect);
            if (collect.size() < page) {
                break;
            }
        }
    }
    
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
        
        final String key = "course_of_id_" + courseIdentifierQuery.getCourseId();
        if (courseIdentifierQuery.getBanned()) {
            redisTemplateCourseKeys.opsForZSet().remove("course_available_keys", key);
        } else {
            redisTemplateCourseKeys.opsForZSet().add("course_available_keys", key, courseIdentifierQuery.getCourseId());
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
        
        final String key = "course_of_id_" + coursePreviewImgUrlQuery.getId();
        redisTemplateCourse.delete(key);
    }
    
    @Override
    public PageModel<CourseDTO> getCourseListPaged(PageQuery pageQuery) {
        // 获得 id 和 key
        final int start = pageQuery.getPageIdx() * pageQuery.getPageSize();
        Set<TypedTuple<String>> courseKeyCacheSet = redisTemplateCourseKeys.opsForZSet()
                                                                           .rangeWithScores("course_available_keys",
                                                                                            start, start +
                                                                                                   pageQuery.getPageSize() -
                                                                                                   1);
        List<Integer> ids = new ArrayList<>(courseKeyCacheSet.size());
        List<String> keys = new ArrayList<>(courseKeyCacheSet.size());
        for (final TypedTuple<String> t : courseKeyCacheSet) {
            ids.add(t.getScore().intValue());
            keys.add(t.getValue());
        }
        
        // 获得缺失的元素
        List<Integer> missingIds = new ArrayList<>(courseKeyCacheSet.size());
        List<Integer> missingIdx = new ArrayList<>(courseKeyCacheSet.size());
        final List<CourseDTO> courseDTOList = redisTemplateCourse.opsForValue().multiGet(keys);
        for (int i = 0; i < courseDTOList.size(); i++) {
            if (courseDTOList.get(i) == null) {
                missingIds.add(ids.get(i));
                missingIdx.add(i);
            }
        }
        if (!missingIds.isEmpty()) {
            // 查找缺失的元素
            final List<CourseDO> courseDOS = courseDao.selectListByIdArray(missingIds.toArray(new Integer[0]));
            final List<CourseDTO> courseDTOS = courseConverter.listCourseDO2CourseDTO(courseDOS);
            for (int i = 0; i < missingIdx.size(); i++) {
                courseDTOList.set(missingIdx.get(i), courseDTOS.get(i));
            }
            
            // 回写缺失的元素
            final Random random = new Random();
            for (int i = 0; i < missingIdx.size(); i++) {
                redisTemplateCourse.opsForValue()
                                   .set(keys.get(missingIdx.get(i)), courseDTOS.get(i), 10 * 60 + random.nextInt(10),
                                        TimeUnit.SECONDS);
            }
        }
        
        final long totalNum =
                Optional.ofNullable(redisTemplateCourseKeys.opsForZSet().size("course_available_keys")).orElse(0L);
        // 返回结果
        final PageModel<CourseDTO> pageModel = new PageModel<>();
        pageModel.setTotalNum(totalNum);
        pageModel.setData(courseDTOList);
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
    
    @Override
    public List<CourseDTO> selectByTitle(String title) {
        final List<CourseDO> courseDOS = courseDao.selectByTitle(title);
        final List<CourseDTO> courseDTOS = courseConverter.listCourseDO2CourseDTO(courseDOS);
        return courseDTOS;
    }
    
}
