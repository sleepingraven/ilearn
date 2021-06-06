package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/22
 */
@Data
public class DiscussionReplyQuery {
    private Integer level;
    private String content;
    private Integer userId;
    private Integer replyTo;
    
}
