package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/18
 */
@Data
public class UserEmailQuery {
    /**
     * 用户 id
     */
    private Integer id;
    private String email;
    
    public UserEmailQuery(Integer id, String email) {
        this.id = id;
        this.email = email;
    }
    
}
