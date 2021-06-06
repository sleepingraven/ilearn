package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/22
 */
@Data
public class PostPublishmentQuery {
    private String title;
    private String content;
    private Integer courseId;
    private Integer userId;
    
}
