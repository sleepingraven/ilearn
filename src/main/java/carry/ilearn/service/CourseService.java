package carry.ilearn.service;

import carry.ilearn.common.error.BusinessException;
import carry.ilearn.model.PageModel;
import carry.ilearn.query.*;
import carry.ilearn.service.datatransferobject.CourseDTO;
import carry.ilearn.service.datatransferobject.CourseReferDTO;

import java.util.List;

/**
 * @author Carry
 * @since 2021/5/17
 */
public interface CourseService {
    
    void addCourse(CourseCreationQuery courseCreationQuery);
    
    void banCourse(CourseIdentifierQuery courseIdentifierQuery) throws BusinessException;
    
    List<CourseDTO> getCourseListByUser(Integer userId);
    
    CourseDTO getCourseById(Integer courseId) throws BusinessException;
    
    void updateCourseOverview(CourseOverviewQuery courseOverviewQuery) throws BusinessException;
    
    void updateCoursePreviewImgUrl(CoursePreviewImgUrlQuery coursePreviewImgUrlQuery) throws BusinessException;
    
    PageModel<CourseDTO> getCourseListPaged(PageQuery pageQuery);
    
    void addRefer(CourseReferQuery courseReferQuery) throws BusinessException;
    
    List<CourseReferDTO> getRefersByCourseId(Integer courseId);
    
    void deleteRefer(CourseReferDeleteQuery courseReferDeleteQuery);
    
}
