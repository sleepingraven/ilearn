package carry.ilearn.dataobject;

import lombok.Data;

import java.io.Serializable;

/**
 * role
 *
 * @author
 */
@Data
public class RoleDO implements Serializable {
    private Integer id;
    
    private String name;
    private String alias;
    
    private static final long serialVersionUID = 1L;
    
}
