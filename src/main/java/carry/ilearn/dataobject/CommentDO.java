package carry.ilearn.dataobject;

import lombok.Data;

import java.io.Serializable;

/**
 * comment
 *
 * @author
 */
@Data
public class CommentDO implements Serializable {
    private Integer id;
    
    /**
     * 第几级评论。1，2或3以上的数
     */
    private Integer level;
    
    private Integer statementId;
    
    /**
     * 回复给哪个记录。post id 或 comment id
     */
    private Integer replyToId;
    
    /**
     * 指向一级评论，如果已是一级评论则是 -1
     */
    private Integer rootId;
    
    private StatementDO statementDO;
    
    private static final long serialVersionUID = 1L;
    
}
