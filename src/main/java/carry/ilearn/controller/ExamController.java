package carry.ilearn.controller;

import carry.ilearn.common.error.BusinessException;
import carry.ilearn.common.response.CommonReturnType;
import carry.ilearn.converter.ExamConverter;
import carry.ilearn.query.ExamAddQuery;
import carry.ilearn.query.ExamJoinQuery;
import carry.ilearn.query.ExamPageQuery;
import carry.ilearn.service.UserStateService;
import carry.ilearn.service.ExamService;
import carry.ilearn.service.datatransferobject.ExamDTO;
import carry.ilearn.service.datatransferobject.ExamVO;
import carry.ilearn.service.datatransferobject.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Carry
 * @since 2021/5/23
 */
@Slf4j
@RequestMapping("/exam")
@RestController
public class ExamController {
    @Resource
    private UserStateService userStateService;
    @Resource
    private ExamService examService;
    @Resource
    private ExamConverter examConverter;
    
    @PostMapping(path = "/add")
    public CommonReturnType<?> addExam(@RequestBody ExamAddQuery examAddQuery) {
        final UserDTO loginUser = userStateService.getLoginUser();
        examAddQuery.setUserId(loginUser.getId());
        final String code = examService.addExam(examAddQuery);
        return CommonReturnType.create(code);
    }
    
    @GetMapping(path = "/list")
    public CommonReturnType<?> getExamPaged() {
        // todo 分页
        final UserDTO loginUser = userStateService.getLoginUser();
        ExamPageQuery examPageQuery = new ExamPageQuery();
        examPageQuery.setUserId(loginUser.getId());
        final List<ExamDTO> examDTOList = examService.getExamPaged(examPageQuery);
        final List<ExamVO> examVOS = examConverter.listExamDTO2ExamVO(examDTOList);
        return CommonReturnType.create(examVOS);
    }
    
    @GetMapping(path = "/from-code/{code}")
    public CommonReturnType<?> getExamByCode(@PathVariable("code") String code) {
        final ExamDTO examDTO = examService.getExamByCode(code);
        final ExamVO examVO = examConverter.examDTO2ExamVO(examDTO);
        return CommonReturnType.create(examVO);
    }
    
    @PostMapping(path = "/join")
    public CommonReturnType<?> getExamByCode(@RequestBody ExamJoinQuery examJoinQuery) throws BusinessException {
        final UserDTO loginUser = userStateService.getLoginUser();
        examJoinQuery.setUserId(loginUser.getId());
        examService.doJoinExam(examJoinQuery);
        return CommonReturnType.empty();
    }
    
    @GetMapping(path = "/list/joint")
    public CommonReturnType<?> getExamJoint() throws BusinessException {
        final UserDTO loginUser = userStateService.getLoginUser();
        final List<ExamDTO> examDTOList = examService.getExamJoint(loginUser.getId());
        final List<ExamVO> examVOS = examConverter.listExamDTO2ExamVO(examDTOList);
        return CommonReturnType.create(examVOS);
    }
    
}
