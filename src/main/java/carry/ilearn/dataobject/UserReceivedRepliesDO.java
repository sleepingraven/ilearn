package carry.ilearn.dataobject;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * user_received_replies
 *
 * @author
 */
@Data
public class UserReceivedRepliesDO implements Serializable {
    private Integer id;
    
    /**
     * 发送的评论的 id
     */
    private Integer commentFromId;
    
    private Integer userToId;
    
    private Integer postId;
    
    private UserDO userFromDO;
    private PostDO postDO;
    private Boolean unread;
    private Date createTime;
    
    private static final long serialVersionUID = 1L;
    
}
