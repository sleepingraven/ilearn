package carry.ilearn.controller.viewobject;

import lombok.Data;

import java.util.Date;

/**
 * @author Carry
 * @since 2021/5/25
 */
@Data
public class UserReceivedRepliesVO {
    private Integer id;
    /**
     * 发送的评论的 id
     */
    private Integer commentFromId;
    private Boolean unread;
    private Date createTime;
    
    private Integer postId;
    private String postTitle;
    
    private Integer userFromId;
    private String userFromName;
    
}
