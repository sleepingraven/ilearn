package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/21
 */
@Data
public class CoursewareGroupQuery {
    private Integer id;
    private Integer courseId;
    private Integer userId;
    private String description;
    
}
