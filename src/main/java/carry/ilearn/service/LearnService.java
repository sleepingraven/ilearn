package carry.ilearn.service;

import carry.ilearn.common.error.BusinessException;
import carry.ilearn.model.PageModel;
import carry.ilearn.query.*;
import carry.ilearn.service.datatransferobject.CourseChapterDTO;
import carry.ilearn.service.datatransferobject.CourseDTO;
import carry.ilearn.service.datatransferobject.CoursewareGroupDTO;
import carry.ilearn.service.datatransferobject.LearnAchieveDTO;

import java.util.List;

/**
 * @author Carry
 * @since 2021/5/17
 */
public interface LearnService {
    
    CourseDTO getCourseIntroductionWithOwner(Integer courseId) throws BusinessException;
    
    Integer addIntroduction(CourseIntroductionQuery courseIntroductionQuery) throws BusinessException;
    
    void updateIntroduction(CourseIntroductionQuery courseIntroductionQuery) throws BusinessException;
    
    void deleteIntroduction(CourseIntroductionQuery courseIntroductionQuery) throws BusinessException;
    
    List<CourseChapterDTO> getCourseChapter(Integer courseId) throws BusinessException;
    
    Integer addChapter(CourseChapterQuery courseChapterQuery) throws BusinessException;
    
    void updateChapter(CourseChapterQuery courseChapterQuery) throws BusinessException;
    
    void deleteChapter(CourseChapterQuery courseChapterQuery) throws BusinessException;
    
    Integer addSection(CourseSectionEditQuery courseChapterQuery) throws BusinessException;
    
    void updateSection(CourseSectionEditQuery courseSectionEditQuery) throws BusinessException;
    
    void deleteSection(CourseSectionEditQuery courseSectionEditQuery) throws BusinessException;
    
    void updateSectionPoster(CourseSectionPosterQuery courseSectionPosterQuery) throws BusinessException;
    
    void updateSectionVideo(CourseSectionVideoQuery courseSectionVideoQuery) throws BusinessException;
    
    List<CourseChapterDTO> getCourseChapter(CourseLearnQuery courseLearnQuery) throws BusinessException;
    
    void updateSectionAchieve(CourseLearnAchieveQuery courseLearnAchieveQuery);
    
    Integer addCourseware(CoursewareItemQuery coursewareItemQuery) throws BusinessException;
    
    List<CoursewareGroupDTO> getCourseware(Integer courseId) throws BusinessException;
    
    void deleteCourseware(CoursewareItemQuery coursewareItemQuery) throws BusinessException;
    
    void updateCoursewareGroup(CoursewareGroupQuery coursewareGroupQuery) throws BusinessException;
    
    PageModel<LearnAchieveDTO> getLearnAchievePaged(LearnAchievePageQuery learnAchievePageQuery);
    
}
