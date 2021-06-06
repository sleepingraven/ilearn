package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/25
 */
@Data
public class ReplyReceivedPageQuery {
    private Integer userId;
    private Integer beginId;
    private Integer offset;
    
}
