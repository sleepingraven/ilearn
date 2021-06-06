package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/19
 */
@Data
public class CourseChapterQuery {
    private Integer id;
    private String title;
    private String description;
    
    private Integer courseId;
    private Integer userId;
    
}
