package carry.ilearn.query;

import lombok.Data;

import java.util.Date;

/**
 * @author Carry
 * @since 2021/5/18
 */
@Data
public class UserProfileQuery {
    private Integer id;
    private String name;
    private Byte gender;
    private Date birthday;
    private String description;
    
}
