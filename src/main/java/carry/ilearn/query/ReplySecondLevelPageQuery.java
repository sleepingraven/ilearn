package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/22
 */
@Data
public class ReplySecondLevelPageQuery {
    private Integer commentId;
    private Integer secondPageIdx;
    private Integer secondPageSize;
    private Integer userId;
    
}
