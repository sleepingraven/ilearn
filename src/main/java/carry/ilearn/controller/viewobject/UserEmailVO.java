package carry.ilearn.controller.viewobject;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/18
 */
@Data
public class UserEmailVO {
    private String mainEmail;
    private String[] otherEmails;
    
}
