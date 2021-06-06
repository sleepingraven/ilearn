package carry.ilearn.controller;

import carry.ilearn.common.error.BusinessException;
import carry.ilearn.common.response.CommonReturnType;
import carry.ilearn.controller.viewobject.CourseOverviewVO;
import carry.ilearn.controller.viewobject.CoursePreviewVO;
import carry.ilearn.controller.viewobject.CourseReferVO;
import carry.ilearn.converter.CourseConverter;
import carry.ilearn.model.PageModel;
import carry.ilearn.query.*;
import carry.ilearn.service.UserStateService;
import carry.ilearn.service.CourseService;
import carry.ilearn.service.FileService;
import carry.ilearn.service.datatransferobject.CourseDTO;
import carry.ilearn.service.datatransferobject.CourseReferDTO;
import carry.ilearn.service.datatransferobject.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Carry
 * @since 2021/5/17
 */
@Slf4j
@RequestMapping("/course")
@RestController
public class CourseController {
    @Resource
    private CourseService courseService;
    @Resource
    private FileService fileService;
    @Resource
    private UserStateService userStateService;
    @Resource
    private CourseConverter courseConverter;
    
    @PostMapping(path = "/add")
    public CommonReturnType<?> addCourse(@RequestBody String courseName) {
        final UserDTO loginUser = userStateService.getLoginUser();
        CourseCreationQuery courseCreationQuery = new CourseCreationQuery();
        courseCreationQuery.setUserId(loginUser.getId());
        courseCreationQuery.setTitle(courseName);
        courseService.addCourse(courseCreationQuery);
        return CommonReturnType.empty();
    }
    
    /**
     * axios 用 data 传值会报错：required request body is missing。
     * 需要用 String 接收：@RequestBody String courseId
     * 用 REST 工具也可以接收
     */
    @PatchMapping(path = "/ban")
    public CommonReturnType<?> removeCourse(@RequestBody CourseIdentifierQuery courseIdentifierQuery)
            throws BusinessException {
        final UserDTO loginUser = userStateService.getLoginUser();
        courseIdentifierQuery.setUserId(loginUser.getId());
        courseService.banCourse(courseIdentifierQuery);
        return CommonReturnType.empty();
    }
    
    @GetMapping(path = "/preview-mine")
    public CommonReturnType<?> getCoursePreviewListByUser() {
        final UserDTO loginUser = userStateService.getLoginUser();
        final List<CourseDTO> courseList = courseService.getCourseListByUser(loginUser.getId());
        final List<CoursePreviewVO> coursePreviewVOS = courseConverter.listCourseDTO2CoursePreviewVO(courseList);
        return CommonReturnType.create(coursePreviewVOS);
    }
    
    @GetMapping(path = "/overview/{courseId}")
    public CommonReturnType<?> getCourseOverview(@PathVariable Integer courseId) throws BusinessException {
        CourseDTO courseDTO = courseService.getCourseById(courseId);
        final CourseOverviewVO courseOverviewVO = courseConverter.courseDTO2CourseOverviewVO(courseDTO);
        
        final List<CourseReferDTO> courseReferDTOs = courseService.getRefersByCourseId(courseId);
        final List<CourseReferVO> courseReferVOS = courseConverter.listCourseReferDTO2CourseReferVO(courseReferDTOs);
        courseOverviewVO.setRefers(courseReferVOS);
        
        return CommonReturnType.create(courseOverviewVO);
    }
    
    @PatchMapping(path = "/overview")
    public CommonReturnType<?> updateCourseOverview(@RequestBody CourseOverviewQuery courseOverviewQuery)
            throws BusinessException {
        final UserDTO loginUser = userStateService.getLoginUser();
        courseOverviewQuery.setUserId(loginUser.getId());
        courseService.updateCourseOverview(courseOverviewQuery);
        return CommonReturnType.empty();
    }
    
    @PostMapping(path = "/upload-preview-img")
    public CommonReturnType<?> uploadPreviewImg(@RequestParam(name = "file") MultipartFile file,
            @RequestParam(name = "courseId") Integer courseId) {
        UserDTO loginUser = userStateService.getLoginUser();
        try {
            String url = fileService.saveFile(new FileSaveQuery(file, "images")).getUrl();
            CoursePreviewImgUrlQuery coursePreviewImgUrlQuery = new CoursePreviewImgUrlQuery();
            coursePreviewImgUrlQuery.setUserId(loginUser.getId());
            coursePreviewImgUrlQuery.setId(courseId);
            coursePreviewImgUrlQuery.setUrl(url);
            courseService.updateCoursePreviewImgUrl(coursePreviewImgUrlQuery);
            return CommonReturnType.create(url);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return CommonReturnType.withError("上传失败");
        }
    }
    
    @GetMapping(path = "/preview/list")
    public CommonReturnType<?> getCoursePreviewListPaged(PageQuery pageQuery) {
        final PageModel<CourseDTO> pageModel = courseService.getCourseListPaged(pageQuery);
        final List<CoursePreviewVO> coursePreviewVOS =
                courseConverter.listCourseDTO2CoursePreviewVO(pageModel.getData());
        
        PageModel<CoursePreviewVO> previewVOPageModel = new PageModel<>();
        previewVOPageModel.setTotalNum(pageModel.getTotalNum());
        previewVOPageModel.setData(coursePreviewVOS);
        return CommonReturnType.create(previewVOPageModel);
    }
    
    @GetMapping(path = "/preview/{courseId}")
    public CommonReturnType<?> getCoursePreview(@PathVariable Integer courseId) throws BusinessException {
        CourseDTO courseDTO = courseService.getCourseById(courseId);
        final CoursePreviewVO coursePreviewVO = courseConverter.courseDTO2CoursePreviewVO(courseDTO);
        return CommonReturnType.create(coursePreviewVO);
    }
    
    @PostMapping(path = "/refer")
    public CommonReturnType<?> addRefer(@RequestBody CourseReferQuery courseReferQuery) throws BusinessException {
        UserDTO loginUser = userStateService.getLoginUser();
        courseReferQuery.setUserId(loginUser.getId());
        courseService.addRefer(courseReferQuery);
        return CommonReturnType.empty();
    }
    
    @DeleteMapping(path = "/refer")
    public CommonReturnType<?> deleteRefer(@RequestBody CourseReferDeleteQuery courseReferDeleteQuery) {
        UserDTO loginUser = userStateService.getLoginUser();
        courseReferDeleteQuery.setUserId(loginUser.getId());
        courseService.deleteRefer(courseReferDeleteQuery);
        return CommonReturnType.empty();
    }
    
}
