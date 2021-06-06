package carry.ilearn.service;

import carry.ilearn.service.datatransferobject.UserDTO;

/**
 * @author Carry
 * @since 2021/5/18
 */
public interface UserStateService {
    
    UserDTO loadUser(String email);
    
    void saveUser(UserDTO loginUser);
    
    UserDTO getLoginUser();
    
}
