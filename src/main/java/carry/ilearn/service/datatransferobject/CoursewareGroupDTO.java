package carry.ilearn.service.datatransferobject;

import lombok.Data;

import java.util.List;

/**
 * @author Carry
 * @since 2021/5/21
 */
@Data
public class CoursewareGroupDTO {
    private Integer id;
    private String description;
    
    private List<CoursewareItemDTO> coursewareItemDTOList;
    
}
