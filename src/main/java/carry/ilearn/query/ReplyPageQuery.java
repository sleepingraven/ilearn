package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/22
 */
@Data
public class ReplyPageQuery {
    private Integer postId;
    private Integer pageIdx;
    private Integer pageSize;
    private Integer secondPageSize;
    private Integer userId;
    
}
