package carry.ilearn.service;

import carry.ilearn.common.error.BusinessException;
import carry.ilearn.query.ExamAddQuery;
import carry.ilearn.query.ExamJoinQuery;
import carry.ilearn.query.ExamPageQuery;
import carry.ilearn.service.datatransferobject.ExamDTO;

import java.util.List;

/**
 * @author Carry
 * @since 2021/5/23
 */
public interface ExamService {
    
    String addExam(ExamAddQuery examAddQuery);
    
    List<ExamDTO> getExamPaged(ExamPageQuery examPageQuery);
    
    ExamDTO getExamByCode(String code);
    
    void doJoinExam(ExamJoinQuery examJoinQuery) throws BusinessException;
    
    List<ExamDTO> getExamJoint(Integer userId) throws BusinessException;
    
}
