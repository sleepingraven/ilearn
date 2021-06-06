package carry.ilearn.controller.viewobject;

import lombok.Data;

import java.util.List;

/**
 * @author Carry
 * @since 2021/5/21
 */
@Data
public class CoursewareGroupVO {
    private Integer id;
    private String description;
    
    private List<CoursewareItemVO> coursewares;
    
}
