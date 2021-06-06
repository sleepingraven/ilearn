package carry.ilearn.service;

import carry.ilearn.common.error.BusinessException;
import carry.ilearn.query.*;
import carry.ilearn.service.datatransferobject.UserDTO;

import java.util.List;

/**
 * @author Carry
 * @since 2021/5/14
 */
public interface UserService {
    
    void doSignUp(SignQuery signQuery) throws BusinessException;
    
    void updateAvatarUrl(UserAvatarQuery userAvatarQuery);
    
    void updateUser(UserProfileQuery userProfileQuery);
    
    List<String> getEmails(Integer userId);
    
    void addEmail(UserEmailQuery userEmailQuery) throws BusinessException;
    
    UserDTO getUserByEmail(String email) throws BusinessException;
    
    int updateMainEmail(UserEmailQuery userEmailQuery) throws BusinessException;
    
    void removeEmail(UserEmailQuery userEmailQuery) throws BusinessException;
    
    void changePassword(UserPasswordQuery userPasswordQuery) throws Exception;
    
}
