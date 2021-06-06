package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/21
 */
@Data
public class CoursewareItemQuery {
    private Integer id;
    private Integer groupId;
    private Integer courseId;
    private Integer userId;
    private String title;
    private String url;
    
}
