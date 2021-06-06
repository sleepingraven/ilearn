package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/19
 */
@Data
public class CourseIntroductionQuery {
    private Integer id;
    private String title;
    private String content;
    private Integer courseId;
    private Integer userId;
    
}
