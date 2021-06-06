package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/19
 */
@Data
public class CourseIdentifierQuery {
    private Integer courseId;
    private Integer userId;
    private Boolean banned;
    
}
