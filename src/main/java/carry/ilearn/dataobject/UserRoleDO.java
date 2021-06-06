package carry.ilearn.dataobject;

import java.io.Serializable;
import lombok.Data;

/**
 * user_role
 * @author 
 */
@Data
public class UserRoleDO implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer roleId;

    private static final long serialVersionUID = 1L;
}