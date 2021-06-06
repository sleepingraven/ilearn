package carry.ilearn.dataobject;

import java.io.Serializable;
import lombok.Data;

/**
 * user_received_replies_unread
 * @author 
 */
@Data
public class UserReceivedRepliesUnreadDO implements Serializable {
    private Integer id;

    private Integer receivedId;

    private static final long serialVersionUID = 1L;
}