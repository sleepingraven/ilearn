package carry.ilearn.converter;

import carry.ilearn.controller.viewobject.CourseOverviewVO;
import carry.ilearn.controller.viewobject.CoursePreviewVO;
import carry.ilearn.controller.viewobject.CourseReferVO;
import carry.ilearn.converter.CourseConverter.ByteBooleanMapper;
import carry.ilearn.dataobject.CourseDO;
import carry.ilearn.dataobject.CourseReferDO;
import carry.ilearn.query.CourseCreationQuery;
import carry.ilearn.service.datatransferobject.CourseDTO;
import carry.ilearn.service.datatransferobject.CourseReferDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Carry
 * @since 2021/5/18
 */
@Mapper(componentModel = "spring", uses = ByteBooleanMapper.class)
public interface CourseConverter {
    
    CourseOverviewVO courseDTO2CourseOverviewVO(CourseDTO courseDTO);
    
    CoursePreviewVO courseDTO2CoursePreviewVO(CourseDTO courseDTO);
    
    List<CoursePreviewVO> listCourseDTO2CoursePreviewVO(List<CourseDTO> courseDTOList);
    
    CourseDTO courseDO2CourseDTO(CourseDO courseDO);
    
    List<CourseDTO> listCourseDO2CourseDTO(List<CourseDO> courseDOList);
    
    CourseDO courseCreationQuery2CourseDO(CourseCreationQuery courseCreationQuery);
    
    CourseReferDTO courseReferDO2CourseReferDTO(CourseReferDO courseReferDO);
    
    List<CourseReferDTO> listCourseReferDO2CourseReferDTO(List<CourseReferDO> courseReferDOList);
    
    CourseReferVO courseReferDTO2CourseReferVO(CourseReferDTO courseReferDTO);
    
    List<CourseReferVO> listCourseReferDTO2CourseReferVO(List<CourseReferDTO> courseReferDTOList);
    
    @Component
    class ByteBooleanMapper {
        
        public Byte asByte(Boolean bool) {
            return bool ? (byte) 1 : 0;
        }
        
        public Boolean asBoolean(Byte b) {
            return b != 0;
        }
        
    }
    
}
