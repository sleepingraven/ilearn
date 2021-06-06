package carry.ilearn.controller;

import carry.ilearn.common.error.BusinessException;
import carry.ilearn.common.response.CommonReturnType;
import carry.ilearn.controller.viewobject.*;
import carry.ilearn.converter.LearnConverter;
import carry.ilearn.model.PageModel;
import carry.ilearn.query.*;
import carry.ilearn.service.UserStateService;
import carry.ilearn.service.FileService;
import carry.ilearn.service.LearnService;
import carry.ilearn.service.datatransferobject.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Carry
 * @since 2021/5/17
 */
@Slf4j
@RequestMapping("/learn")
@RestController
public class LearnController {
    @Resource
    private LearnService learnService;
    @Resource
    private FileService fileService;
    @Resource
    private UserStateService userStateService;
    @Resource
    private LearnConverter learnConverter;
    
    @GetMapping(path = "/introduction/{courseId}")
    public CommonReturnType<?> getCourseIntroduction(@PathVariable Integer courseId) throws BusinessException {
        CourseDTO courseDTO = learnService.getCourseIntroductionWithOwner(courseId);
        final CourseIntroductionVO courseIntroductionVO = learnConverter.courseDTO2CourseIntroductionVO(courseDTO);
        return CommonReturnType.create(courseIntroductionVO);
    }
    
    @PostMapping(path = "/upload-learn-img")
    public CommonReturnType<?> uploadLearnImg(@RequestParam(name = "image") MultipartFile file) {
        userStateService.getLoginUser();
        try {
            String url = fileService.saveFile(new FileSaveQuery(file, "images")).getUrl();
            return CommonReturnType.create(url);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return CommonReturnType.withError("上传失败");
        }
    }
    
    @PostMapping(path = "/introduction")
    public CommonReturnType<?> addIntroduction(@RequestBody CourseIntroductionQuery courseIntroductionQuery)
            throws BusinessException {
        UserDTO loginUser = userStateService.getLoginUser();
        courseIntroductionQuery.setUserId(loginUser.getId());
        final Integer id = learnService.addIntroduction(courseIntroductionQuery);
        return CommonReturnType.create(id);
    }
    
    @PutMapping(path = "/introduction")
    public CommonReturnType<?> updateIntroduction(@RequestBody CourseIntroductionQuery courseIntroductionQuery)
            throws BusinessException {
        UserDTO loginUser = userStateService.getLoginUser();
        courseIntroductionQuery.setUserId(loginUser.getId());
        learnService.updateIntroduction(courseIntroductionQuery);
        return CommonReturnType.empty();
    }
    
    @DeleteMapping(path = "/introduction")
    public CommonReturnType<?> deleteIntroduction(@RequestBody CourseIntroductionQuery courseIntroductionQuery)
            throws BusinessException {
        UserDTO loginUser = userStateService.getLoginUser();
        courseIntroductionQuery.setUserId(loginUser.getId());
        learnService.deleteIntroduction(courseIntroductionQuery);
        return CommonReturnType.empty();
    }
    
    @GetMapping(path = "/chapter/{courseId}")
    public CommonReturnType<?> getCourseChapter(@PathVariable Integer courseId) throws BusinessException {
        final UserDTO loginUser = userStateService.getLoginUser();
        List<CourseChapterDTO> courseChapterDTOs;
        if (loginUser != null) {
            final CourseLearnQuery courseLearnQuery = new CourseLearnQuery();
            courseLearnQuery.setCourseId(courseId);
            courseLearnQuery.setUserId(loginUser.getId());
            courseChapterDTOs = learnService.getCourseChapter(courseLearnQuery);
        } else {
            courseChapterDTOs = learnService.getCourseChapter(courseId);
        }
        
        // todo 替代 map key
        final List<CourseChapterVO> courseChapterVOS = learnConverter.listChapterDO2CourseChapterVO(courseChapterDTOs);
        final Map<String, Object> resMap = new HashMap<>();
        resMap.put("chapters", courseChapterVOS);
        resMap.put("sign", loginUser != null);
        return CommonReturnType.create(resMap);
    }
    
    @PostMapping(path = "/chapter")
    public CommonReturnType<?> addChapter(@RequestBody CourseChapterQuery courseChapterQuery) throws BusinessException {
        UserDTO loginUser = userStateService.getLoginUser();
        courseChapterQuery.setUserId(loginUser.getId());
        final Integer chapterId = learnService.addChapter(courseChapterQuery);
        return CommonReturnType.create(chapterId);
    }
    
    @PutMapping(path = "/chapter")
    public CommonReturnType<?> updateChapter(@RequestBody CourseChapterQuery courseChapterQuery)
            throws BusinessException {
        UserDTO loginUser = userStateService.getLoginUser();
        courseChapterQuery.setUserId(loginUser.getId());
        learnService.updateChapter(courseChapterQuery);
        return CommonReturnType.empty();
    }
    
    @DeleteMapping(path = "/chapter")
    public CommonReturnType<?> deleteChapter(@RequestBody CourseChapterQuery courseChapterQuery)
            throws BusinessException {
        UserDTO loginUser = userStateService.getLoginUser();
        courseChapterQuery.setUserId(loginUser.getId());
        learnService.deleteChapter(courseChapterQuery);
        return CommonReturnType.empty();
    }
    
    @PostMapping(path = "/section")
    public CommonReturnType<?> addSection(@RequestBody CourseSectionEditQuery courseSectionEditQuery)
            throws BusinessException {
        UserDTO loginUser = userStateService.getLoginUser();
        courseSectionEditQuery.setUserId(loginUser.getId());
        final Integer sectionId = learnService.addSection(courseSectionEditQuery);
        return CommonReturnType.create(sectionId);
    }
    
    @PatchMapping(path = "/section")
    public CommonReturnType<?> updateSection(@RequestBody CourseSectionEditQuery courseSectionEditQuery)
            throws BusinessException {
        UserDTO loginUser = userStateService.getLoginUser();
        courseSectionEditQuery.setUserId(loginUser.getId());
        learnService.updateSection(courseSectionEditQuery);
        return CommonReturnType.empty();
    }
    
    @DeleteMapping(path = "/section")
    public CommonReturnType<?> deleteSection(@RequestBody CourseSectionEditQuery courseSectionEditQuery)
            throws BusinessException {
        UserDTO loginUser = userStateService.getLoginUser();
        courseSectionEditQuery.setUserId(loginUser.getId());
        learnService.deleteSection(courseSectionEditQuery);
        return CommonReturnType.empty();
    }
    
    @PostMapping(path = "/section/upload-poster")
    public CommonReturnType<?> uploadSectionPoster(@RequestParam(name = "file") MultipartFile file,
            @RequestParam("id") Integer id, @RequestParam("chapterId") Integer chapterId) {
        UserDTO loginUser = userStateService.getLoginUser();
        String url;
        try {
            url = fileService.saveFile(new FileSaveQuery(file, "images")).getUrl();
            CourseSectionPosterQuery courseSectionPosterQuery = new CourseSectionPosterQuery();
            courseSectionPosterQuery.setId(id);
            courseSectionPosterQuery.setChapterId(chapterId);
            courseSectionPosterQuery.setUserId(loginUser.getId());
            courseSectionPosterQuery.setPosterFileName(file.getOriginalFilename());
            courseSectionPosterQuery.setPosterUrl(url);
            learnService.updateSectionPoster(courseSectionPosterQuery);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return CommonReturnType.withError("上传失败");
        }
        return CommonReturnType.create(url);
    }
    
    @PostMapping(path = "/section/upload-video")
    public CommonReturnType<?> uploadSectionVideo(@RequestParam(name = "file") MultipartFile file,
            @RequestParam("id") Integer id, @RequestParam("chapterId") Integer chapterId) {
        UserDTO loginUser = userStateService.getLoginUser();
        try {
            FileDTO fileDTO = fileService.saveFile(new FileSaveQuery(file, "videos"));
            CourseSectionVideoQuery courseSectionVideoQuery = new CourseSectionVideoQuery();
            courseSectionVideoQuery.setId(id);
            courseSectionVideoQuery.setChapterId(chapterId);
            courseSectionVideoQuery.setUserId(loginUser.getId());
            courseSectionVideoQuery.setVideoUrl(fileDTO.getUrl());
            MultimediaObject instance = new MultimediaObject(fileDTO.getFile());
            MultimediaInfo multimediaInfo = instance.getInfo();
            final int seconds = (int) (multimediaInfo.getDuration() / 1000);
            courseSectionVideoQuery.setSeconds(seconds);
            learnService.updateSectionVideo(courseSectionVideoQuery);
            
            final CourseSectionVideoVO courseSectionVideoVO = new CourseSectionVideoVO();
            courseSectionVideoVO.setVideoUrl(fileDTO.getUrl());
            courseSectionVideoVO.setSeconds(seconds);
            return CommonReturnType.create(courseSectionVideoVO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return CommonReturnType.withError("上传失败");
        }
    }
    
    @PostMapping(path = "/achieve")
    public CommonReturnType<?> updateSectionLearn(@RequestBody CourseLearnAchieveQuery courseLearnAchieveQuery) {
        UserDTO loginUser = userStateService.getLoginUser();
        courseLearnAchieveQuery.setUserId(loginUser.getId());
        learnService.updateSectionAchieve(courseLearnAchieveQuery);
        return CommonReturnType.empty();
    }
    
    @GetMapping(path = "/courseware/{courseId}")
    public CommonReturnType<?> getCourseware(@PathVariable Integer courseId) throws BusinessException {
        final List<CoursewareGroupDTO> coursewareGroupDTOS = learnService.getCourseware(courseId);
        final List<CoursewareGroupVO> courseIntroductionVOs =
                learnConverter.coursewareGroupDTOList2CoursewareGroupVO(coursewareGroupDTOS);
        return CommonReturnType.create(courseIntroductionVOs);
    }
    
    @PostMapping(path = "/courseware/upload")
    public CommonReturnType<?> uploadCourseware(@RequestParam(name = "file") MultipartFile file,
            @RequestParam("groupId") Integer groupId, @RequestParam("courseId") Integer courseId) {
        UserDTO loginUser = userStateService.getLoginUser();
        try {
            String url = fileService.saveFile(new FileSaveQuery(file, "courseware", "download")).getUrl();
            CoursewareItemQuery coursewareItemQuery = new CoursewareItemQuery();
            coursewareItemQuery.setGroupId(groupId);
            coursewareItemQuery.setCourseId(courseId);
            coursewareItemQuery.setUserId(loginUser.getId());
            coursewareItemQuery.setTitle(file.getOriginalFilename());
            coursewareItemQuery.setUrl(url);
            final Integer id = learnService.addCourseware(coursewareItemQuery);
            return CommonReturnType.create(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return CommonReturnType.withError("上传失败");
        }
    }
    
    @DeleteMapping(path = "/courseware")
    public CommonReturnType<?> deleteCourseware(@RequestBody CoursewareItemQuery coursewareItemQuery)
            throws BusinessException {
        UserDTO loginUser = userStateService.getLoginUser();
        coursewareItemQuery.setUserId(loginUser.getId());
        learnService.deleteCourseware(coursewareItemQuery);
        return CommonReturnType.empty();
    }
    
    @PatchMapping(path = "/courseware-group")
    public CommonReturnType<?> updateCoursewareGroup(@RequestBody CoursewareGroupQuery coursewareGroupQuery)
            throws BusinessException {
        UserDTO loginUser = userStateService.getLoginUser();
        coursewareGroupQuery.setUserId(loginUser.getId());
        learnService.updateCoursewareGroup(coursewareGroupQuery);
        return CommonReturnType.empty();
    }
    
    @GetMapping(path = "/achieve/list")
    public CommonReturnType<?> getAchieveList(LearnAchievePageQuery learnAchievePageQuery) {
        final UserDTO loginUser = userStateService.getLoginUser();
        learnAchievePageQuery.setUserId(loginUser.getId());
        final PageModel<LearnAchieveDTO> learnAchievePaged = learnService.getLearnAchievePaged(learnAchievePageQuery);
        
        final PageModel<LearnAchieveVO> pageModel = new PageModel<>();
        final List<LearnAchieveVO> learnAchieveVOS =
                learnConverter.listLearnAchieveDTO2LearnAchieveVO(learnAchievePaged.getData());
        pageModel.setData(learnAchieveVOS);
        pageModel.setTotalNum(learnAchievePaged.getTotalNum());
        return CommonReturnType.create(pageModel);
    }
    
}
