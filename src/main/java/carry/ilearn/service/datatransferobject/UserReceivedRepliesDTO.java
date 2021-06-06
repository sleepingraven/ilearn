package carry.ilearn.service.datatransferobject;

import lombok.Data;

import java.util.Date;

/**
 * @author Carry
 * @since 2021/5/25
 */
@Data
public class UserReceivedRepliesDTO {
    private Integer id;
    /**
     * 发送的评论的 id
     */
    private Integer commentFromId;
    
    private Integer postId;
    private String postTitle;
    private Boolean unread;
    private Date createTime;
    
    private Integer userFromId;
    private String userFromName;
    
}
