package carry.ilearn.dataobject;

import lombok.Data;

import java.io.Serializable;

/**
 * email
 *
 * @author
 */
@Data
public class EmailDO implements Serializable {
    private Integer id;
    
    private String email;
    
    private Integer userId;
    
    private static final long serialVersionUID = 1L;
    
}
