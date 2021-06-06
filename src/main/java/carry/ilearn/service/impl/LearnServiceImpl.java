package carry.ilearn.service.impl;

import carry.ilearn.common.error.BusinessException;
import carry.ilearn.converter.CourseConverter;
import carry.ilearn.converter.LearnConverter;
import carry.ilearn.converter.UserConverter;
import carry.ilearn.dao.*;
import carry.ilearn.dataobject.*;
import carry.ilearn.model.PageModel;
import carry.ilearn.query.*;
import carry.ilearn.service.LearnService;
import carry.ilearn.service.datatransferobject.*;
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
public class LearnServiceImpl implements LearnService {
    @Resource
    private UserDao userDao;
    @Resource
    private CourseIntroductionDao courseIntroductionDao;
    @Resource
    private UserConverter userConverter;
    @Resource
    private CourseConverter courseConverter;
    @Resource
    private LearnConverter learnConverter;
    @Resource
    private CourseLearnServiceUtil courseLearnServiceUtil;
    @Resource
    private ChapterDao chapterDao;
    @Resource
    private SectionDao sectionDao;
    @Resource
    private LearnDao learnDao;
    @Resource
    private CoursewareGroupDao coursewareGroupDao;
    @Resource
    private CoursewareItemDao coursewareItemDao;
    
    @Override
    public CourseDTO getCourseIntroductionWithOwner(Integer courseId) throws BusinessException {
        final CourseDO courseDO = courseLearnServiceUtil.getCourseWithBannedCheck(courseId);
        final CourseDTO courseDTO = courseConverter.courseDO2CourseDTO(courseDO);
        
        final UserDO userDO = userDao.selectByPrimaryKey(courseDO.getUserId());
        courseDTO.setUserDTO(userConverter.userDO2UserDTO(userDO));
        
        final List<CourseIntroductionDO> courseIntroductionDOS = courseIntroductionDao.selectByCourseId(courseId);
        final List<CourseIntroductionItemDTO> courseIntroductionItemDTOS =
                learnConverter.listCourseIntroductionDO2CourseIntroductionItemDTO(courseIntroductionDOS);
        courseDTO.setIntroductions(courseIntroductionItemDTOS);
        
        return courseDTO;
    }
    
    @Override
    public Integer addIntroduction(CourseIntroductionQuery courseIntroductionQuery) throws BusinessException {
        courseLearnServiceUtil.checkCourseOwner(courseIntroductionQuery.getUserId(),
                                                courseIntroductionQuery.getCourseId());
        final CourseIntroductionDO courseIntroductionDO =
                learnConverter.courseIntroductionQuery2CourseIntroductionDO(courseIntroductionQuery);
        courseIntroductionDao.insertSelective(courseIntroductionDO);
        return courseIntroductionDO.getId();
    }
    
    @Override
    public void updateIntroduction(CourseIntroductionQuery courseIntroductionQuery) throws BusinessException {
        courseLearnServiceUtil.checkCourseOwner(courseIntroductionQuery.getUserId(),
                                                courseIntroductionQuery.getCourseId());
        final CourseIntroductionDO courseIntroductionDO =
                learnConverter.courseIntroductionQuery2CourseIntroductionDO(courseIntroductionQuery);
        courseIntroductionDao.updateByPrimaryKeySelective(courseIntroductionDO);
    }
    
    @Override
    public void deleteIntroduction(CourseIntroductionQuery courseIntroductionQuery) throws BusinessException {
        courseLearnServiceUtil.checkCourseOwner(courseIntroductionQuery.getUserId(),
                                                courseIntroductionQuery.getCourseId());
        courseIntroductionDao.deleteByPrimaryKey(courseIntroductionQuery.getId());
    }
    
    @Override
    public List<CourseChapterDTO> getCourseChapter(Integer courseId) throws BusinessException {
        courseLearnServiceUtil.getCourseWithBannedCheck(courseId);
        final List<ChapterDO> courseIntroductionDOS = chapterDao.selectByCourseId(courseId);
        final List<CourseChapterDTO> courseIntroductionItemDTOS =
                learnConverter.listChapterDO2CourseIntroductionItemDTO(courseIntroductionDOS);
        
        return courseIntroductionItemDTOS;
    }
    
    @Override
    public Integer addChapter(CourseChapterQuery courseChapterQuery) throws BusinessException {
        courseLearnServiceUtil.checkCourseOwner(courseChapterQuery.getUserId(), courseChapterQuery.getCourseId());
        final ChapterDO chapterDO = learnConverter.courseChapterQuery2CourseChapterQuery(courseChapterQuery);
        chapterDao.insertSelective(chapterDO);
        return chapterDO.getId();
    }
    
    @Override
    public void updateChapter(CourseChapterQuery courseChapterQuery) throws BusinessException {
        courseLearnServiceUtil.checkCourseOwner(courseChapterQuery.getUserId(), courseChapterQuery.getCourseId());
        final ChapterDO chapterDO = learnConverter.courseChapterQuery2CourseChapterQuery(courseChapterQuery);
        chapterDao.updateByPrimaryKeySelective(chapterDO);
    }
    
    @Override
    public void deleteChapter(CourseChapterQuery courseChapterQuery) throws BusinessException {
        courseLearnServiceUtil.checkCourseOwner(courseChapterQuery.getUserId(), courseChapterQuery.getCourseId());
        chapterDao.deleteByPrimaryKey(courseChapterQuery.getId());
        sectionDao.deleteByChapterId(courseChapterQuery.getId());
    }
    
    @Override
    public Integer addSection(CourseSectionEditQuery courseSectionEditQuery) throws BusinessException {
        // fixme 检查 section
        courseLearnServiceUtil.checkChapterOwner(courseSectionEditQuery.getUserId(),
                                                 courseSectionEditQuery.getChapterId());
        final SectionDO sectionDO =
                learnConverter.courseSectionEditQuerycourseSectionQuery2SectionDO(courseSectionEditQuery);
        sectionDao.insertSelective(sectionDO);
        
        return sectionDO.getId();
    }
    
    @Override
    public void updateSection(CourseSectionEditQuery courseSectionEditQuery) throws BusinessException {
        courseLearnServiceUtil.checkChapterOwner(courseSectionEditQuery.getUserId(),
                                                 courseSectionEditQuery.getChapterId());
        final SectionDO sectionDO =
                learnConverter.courseSectionEditQuerycourseSectionQuery2SectionDO(courseSectionEditQuery);
        sectionDao.updateByPrimaryKeySelective(sectionDO);
    }
    
    @Override
    public void deleteSection(CourseSectionEditQuery courseSectionEditQuery) throws BusinessException {
        courseLearnServiceUtil.checkChapterOwner(courseSectionEditQuery.getUserId(),
                                                 courseSectionEditQuery.getChapterId());
        sectionDao.deleteByPrimaryKey(courseSectionEditQuery.getId());
    }
    
    @Override
    public void updateSectionPoster(CourseSectionPosterQuery courseSectionPosterQuery) throws BusinessException {
        courseLearnServiceUtil.checkChapterOwner(courseSectionPosterQuery.getUserId(),
                                                 courseSectionPosterQuery.getChapterId());
        final SectionDO sectionDO = learnConverter.courseSectionPosterQuery2SectionDO(courseSectionPosterQuery);
        sectionDao.updateByPrimaryKeySelective(sectionDO);
    }
    
    @Override
    public void updateSectionVideo(CourseSectionVideoQuery courseSectionVideoQuery) throws BusinessException {
        courseLearnServiceUtil.checkChapterOwner(courseSectionVideoQuery.getUserId(),
                                                 courseSectionVideoQuery.getChapterId());
        final SectionDO sectionDO = learnConverter.courseSectionVideoQuery2SectionDO(courseSectionVideoQuery);
        sectionDao.updateByPrimaryKeySelective(sectionDO);
    }
    
    @Override
    public List<CourseChapterDTO> getCourseChapter(CourseLearnQuery courseLearnQuery) throws BusinessException {
        courseLearnServiceUtil.getCourseWithBannedCheck(courseLearnQuery.getCourseId());
        final List<ChapterDO> courseIntroductionDOS =
                chapterDao.selectByCourseIdWithLearn(courseLearnQuery.getCourseId(), courseLearnQuery.getUserId());
        final List<CourseChapterDTO> courseIntroductionItemDTOS =
                learnConverter.listChapterDO2CourseIntroductionItemDTO(courseIntroductionDOS);
        
        return courseIntroductionItemDTOS;
    }
    
    @Override
    public void updateSectionAchieve(CourseLearnAchieveQuery courseLearnAchieveQuery) {
        try {
            learnDao.insertUserLearn(courseLearnAchieveQuery.getSectionId(), courseLearnAchieveQuery.getUserId(),
                                     courseLearnAchieveQuery.getStatus());
        } catch (Exception e) {
            learnDao.updateUserLearn(courseLearnAchieveQuery.getSectionId(), courseLearnAchieveQuery.getUserId(),
                                     courseLearnAchieveQuery.getStatus());
        }
    }
    
    @Override
    public Integer addCourseware(CoursewareItemQuery coursewareItemQuery) throws BusinessException {
        courseLearnServiceUtil.checkCourseOwner(coursewareItemQuery.getUserId(), coursewareItemQuery.getCourseId());
        final CoursewareItemDO coursewareItemDO = new CoursewareItemDO();
        coursewareItemDO.setTitle(coursewareItemQuery.getTitle());
        coursewareItemDO.setGroupId(coursewareItemQuery.getGroupId());
        coursewareItemDO.setUrl(coursewareItemQuery.getUrl());
        coursewareItemDao.insertSelective(coursewareItemDO);
        return coursewareItemDO.getId();
    }
    
    @Override
    public List<CoursewareGroupDTO> getCourseware(Integer courseId) throws BusinessException {
        courseLearnServiceUtil.getCourseWithBannedCheck(courseId);
        final List<CoursewareGroupDO> coursewareGroupDOS = coursewareGroupDao.selectByCourseId(courseId);
        final List<CoursewareGroupDTO> coursewareGroupDTOS =
                learnConverter.listCoursewareGroupDO2CoursewareGroupDTO(coursewareGroupDOS);
        
        return coursewareGroupDTOS;
    }
    
    @Override
    public void deleteCourseware(CoursewareItemQuery coursewareItemQuery) throws BusinessException {
        courseLearnServiceUtil.checkCourseOwner(coursewareItemQuery.getUserId(), coursewareItemQuery.getCourseId());
        coursewareItemDao.deleteByPrimaryKey(coursewareItemQuery.getId());
    }
    
    @Override
    public void updateCoursewareGroup(CoursewareGroupQuery coursewareGroupQuery) throws BusinessException {
        courseLearnServiceUtil.checkCourseOwner(coursewareGroupQuery.getUserId(), coursewareGroupQuery.getCourseId());
        final CoursewareGroupDO coursewareGroupDO = new CoursewareGroupDO();
        coursewareGroupDO.setId(coursewareGroupQuery.getId());
        coursewareGroupDO.setDescription(coursewareGroupQuery.getDescription());
        coursewareGroupDao.updateByPrimaryKeySelective(coursewareGroupDO);
    }
    
    @Override
    public PageModel<LearnAchieveDTO> getLearnAchievePaged(LearnAchievePageQuery learnAchievePageQuery) {
        final List<LearnAchieveDO> learnAchieveDOS =
                learnDao.selectUserLearnAchievePaged(learnAchievePageQuery.getUserId(),
                                                     learnAchievePageQuery.getPageIdx() *
                                                     learnAchievePageQuery.getPageSize(),
                                                     learnAchievePageQuery.getPageSize());
        final long count = learnDao.selectUserLearnAchieveCount(learnAchievePageQuery.getUserId());
        
        final PageModel<LearnAchieveDTO> pageModel = new PageModel<>();
        final List<LearnAchieveDTO> learnAchieveDTOS =
                learnConverter.listLearnAchieveDO2LearnAchieveDTO(learnAchieveDOS);
        pageModel.setData(learnAchieveDTOS);
        pageModel.setTotalNum(count);
        return pageModel;
    }
    
}
