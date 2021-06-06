package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/22
 */
@Data
public class DiscussionPageQuery {
    private Integer courseId;
    private Integer pageIdx;
    private Integer pageSize;
    
    private Integer userId;
    
}
