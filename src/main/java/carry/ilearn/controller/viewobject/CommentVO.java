package carry.ilearn.controller.viewobject;

import lombok.Data;

import java.util.List;

/**
 * @author Carry
 * @since 2021/5/22
 */
@Data
public class CommentVO {
    private Integer id;
    /**
     * 第几级评论。1，2或3以上的数
     */
    private Integer level;
    private Integer replyToId;
    
    private StatementVO statement;
    private List<CommentVO> comments;
    private Long count;
    
}
