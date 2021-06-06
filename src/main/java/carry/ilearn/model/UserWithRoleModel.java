package carry.ilearn.model;

import lombok.Data;

import java.util.List;

/**
 * @author Carry
 * @since 2021/5/28
 */
@Data
public class UserWithRoleModel {
    private Integer id;
    private String name;
    private List<RoleModel> roles;
    
}
