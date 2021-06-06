package carry.ilearn.dataobject;

import java.io.Serializable;
import lombok.Data;

/**
 * user_like
 * @author 
 */
@Data
public class UserLikeDO implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer statementId;

    private static final long serialVersionUID = 1L;
}