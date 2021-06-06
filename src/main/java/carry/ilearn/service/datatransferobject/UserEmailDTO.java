package carry.ilearn.service.datatransferobject;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/18
 */
@Data
public class UserEmailDTO {
    private String mainEmail;
    private String[] secondaryEmails;
    
}
