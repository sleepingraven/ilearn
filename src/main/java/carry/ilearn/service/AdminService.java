package carry.ilearn.service;

import carry.ilearn.model.UserRoleModel;

/**
 * @author Carry
 * @since 2021/5/28
 */
public interface AdminService {
    
    UserRoleModel getUsers();
    
    void updateUserRole(Integer userId, Integer[] ids);
    
}
