package carry.ilearn.converter;

import carry.ilearn.controller.viewobject.*;
import carry.ilearn.dataobject.*;
import carry.ilearn.query.*;
import carry.ilearn.service.datatransferobject.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author Carry
 * @since 2021/5/19
 */
@Mapper(componentModel = "spring")
public interface LearnConverter {
    
    CourseIntroductionItemDTO courseIntroductionDO2CourseIntroductionItemDTO(CourseIntroductionDO courseIntroductionDO);
    
    List<CourseIntroductionItemDTO> listCourseIntroductionDO2CourseIntroductionItemDTO(
            List<CourseIntroductionDO> courseIntroductionDOList);
    
    CourseIntroductionItemVO courseIntroductionItemDTO2CourseIntroductionItemVO(
            CourseIntroductionItemDTO courseIntroductionItemDTO);
    
    List<CourseIntroductionItemVO> listCourseIntroductionItemDTO2CourseIntroductionItemVO(
            List<CourseIntroductionItemDTO> courseIntroductionItemDTOList);
    
    @Mapping(source = "userDTO.id", target = "userId")
    @Mapping(source = "userDTO.name", target = "userName")
    CourseIntroductionVO courseDTO2CourseIntroductionVO(CourseDTO courseDTO);
    
    CourseIntroductionDO courseIntroductionQuery2CourseIntroductionDO(CourseIntroductionQuery courseIntroductionQuery);
    
    CourseSectionDTO sectionDO2CourseSectionDTO(SectionDO sectionDO);
    
    @Mapping(source = "sectionDOList", target = "courseSectionDTOList")
    CourseChapterDTO chapterDO2CourseIntroductionItemDTO(ChapterDO chapterDO);
    
    List<CourseChapterDTO> listChapterDO2CourseIntroductionItemDTO(List<ChapterDO> courseIntroductionDOList);
    
    @Mapping(source = "courseSectionDTOList", target = "sections")
    CourseChapterVO courseChapterDTO2CourseChapterVO(CourseChapterDTO courseChapterDTO);
    
    List<CourseChapterVO> listChapterDO2CourseChapterVO(List<CourseChapterDTO> chapterDTOList);
    
    ChapterDO courseChapterQuery2CourseChapterQuery(CourseChapterQuery courseChapterQuery);
    
    SectionDO courseSectionEditQuerycourseSectionQuery2SectionDO(CourseSectionEditQuery courseSectionEditQuery);
    
    CourseSectionVO CourseSectionDTO2CourseSectionVO(CourseSectionDTO courseSectionDTO);
    
    SectionDO courseSectionPosterQuery2SectionDO(CourseSectionPosterQuery courseSectionPosterQuery);
    
    SectionDO courseSectionVideoQuery2SectionDO(CourseSectionVideoQuery courseSectionVideoQuery);
    
    CoursewareItemDTO coursewareItemDO2CoursewareItemDTO(CoursewareItemDO coursewareItemDO);
    
    @Mapping(source = "coursewareItemDOList", target = "coursewareItemDTOList")
    CoursewareGroupDTO coursewareGroupDO2CoursewareGroupDTO(CoursewareGroupDO coursewareGroupDO);
    
    List<CoursewareGroupDTO> listCoursewareGroupDO2CoursewareGroupDTO(List<CoursewareGroupDO> courseIntroductionDOList);
    
    CoursewareItemVO coursewareItemDTO2CoursewareItemVO(CoursewareItemDTO coursewareItemDTO);
    
    @Mapping(source = "coursewareItemDTOList", target = "coursewares")
    CoursewareGroupVO coursewareGroupDTO2CoursewareGroupVO(CoursewareGroupDTO coursewareGroupDTO);
    
    List<CoursewareGroupVO> coursewareGroupDTOList2CoursewareGroupVO(List<CoursewareGroupDTO> coursewareGroupDTOList);
    
    @Mapping(source = "courseDO", target = "courseDTO")
    @Mapping(source = "sectionDO", target = "sectionDTO")
    @Mapping(source = "learnProcessDO.finishedTasks", target = "finishedTasks")
    @Mapping(source = "learnProcessDO.totalTasks", target = "totalTasks")
    LearnAchieveDTO learnAchieveDO2LearnAchieveDTO(LearnAchieveDO learnAchieveDO);
    
    List<LearnAchieveDTO> listLearnAchieveDO2LearnAchieveDTO(List<LearnAchieveDO> learnAchieveDOList);
    
    @Mapping(source = "courseDTO.id", target = "courseId")
    @Mapping(source = "courseDTO.title", target = "courseTitle")
    @Mapping(source = "courseDTO.previewImgUrl", target = "previewImgUrl")
    @Mapping(source = "sectionDTO.title", target = "sectionTitle")
    LearnAchieveVO learnAchieveDTO2LearnAchieveVO(LearnAchieveDTO learnAchieveDTO);
    
    List<LearnAchieveVO> listLearnAchieveDTO2LearnAchieveVO(List<LearnAchieveDTO> learnAchieveDTOList);
    
}
