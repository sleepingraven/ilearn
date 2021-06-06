package carry.ilearn.service.datatransferobject;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Carry
 * @since 2021/5/16
 */
@Data
public class UserDTO implements Serializable {
    private Integer id;
    private String name;
    private String avatarUrl;
    /**
     * -1：保密；0：女；1：男
     */
    private Byte gender;
    private Date birthday;
    private String description;
    
    private List<String> roleNames;
    
}
