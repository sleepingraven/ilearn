package carry.ilearn.dataobject;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * statement
 *
 * @author
 */
@Data
public class StatementDO implements Serializable {
    private Integer id;
    
    private Date createTime;
    
    private String content;
    
    private Integer userId;
    
    private Integer totalLikes;
    
    private UserDO user;
    
    private static final long serialVersionUID = 1L;
    
}
