package carry.ilearn.controller.viewobject;

import lombok.Data;

import java.util.Date;

/**
 * @author Carry
 * @since 2021/5/16
 */
@Data
public class UserProfileVO {
    /**
     * -1：保密；0：女；1：男
     */
    private Byte gender;
    private Date birthday;
    private String description;
    
}
