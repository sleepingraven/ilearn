package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/18
 */
@Data
public class UserPasswordQuery {
    private Integer id;
    private String origin;
    private String target;
    private String confirm;
    
}
