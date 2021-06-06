package carry.ilearn.dataobject;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * user
 *
 * @author
 */
@Data
public class UserDO implements Serializable {
    private Integer id;
    
    private String name;
    
    private String avatarUrl;
    
    /**
     * -1：保密；0：女；1：男
     */
    private Byte gender;
    
    private Date birthday;
    
    private String description;
    
    private Integer mainEmailId;
    
    private String ecryptedPassword;
    
    private List<RoleDO> roles;
    
    private static final long serialVersionUID = 1L;
    
}
