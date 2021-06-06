package carry.ilearn.converter;

import carry.ilearn.dataobject.ExamDO;
import carry.ilearn.service.datatransferobject.ExamDTO;
import carry.ilearn.service.datatransferobject.ExamVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Carry
 * @since 2021/5/23
 */
@Mapper(componentModel = "spring")
public interface ExamConverter {
    
    ExamDTO examDO2ExamDTO(ExamDO examDO);
    
    List<ExamDTO> listExamDO2ExamDTO(List<ExamDO> examDOList);
    
    ExamVO examDTO2ExamVO(ExamDTO examDTO);
    
    List<ExamVO> listExamDTO2ExamVO(List<ExamDTO> examDTO);
    
}
