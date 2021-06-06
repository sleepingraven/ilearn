package carry.ilearn.service.impl;

import carry.ilearn.converter.UserConverter;
import carry.ilearn.dao.UserDao;
import carry.ilearn.dataobject.RoleDO;
import carry.ilearn.dataobject.UserDO;
import carry.ilearn.service.UserStateService;
import carry.ilearn.service.datatransferobject.UserDTO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Carry
 * @since 2021/5/18
 */
@Service
public class UserStateServiceImpl implements UserStateService {
    @Resource
    private UserDao userDao;
    @Resource
    private UserConverter userConverter;
    @Resource
    private RedisTemplate<String, UserDTO> userRedisTemplate;
    
    @Override
    public UserDTO loadUser(String email) {
        final UserDO userDO = userDao.selectByEmail(email);
        final UserDTO loginUser = userConverter.userDO2UserDTO(userDO);
        loginUser.setRoleNames(userDO.getRoles().stream().map(RoleDO::getName).collect(Collectors.toList()));
        saveUser(loginUser, email);
        return loginUser;
    }
    
    @Override
    public void saveUser(UserDTO loginUser) {
        final String email = getLoginUserPrimaryEmail();
        saveUser(loginUser, email);
    }
    
    private void saveUser(UserDTO loginUser, String email) {
        // todo 用 hash 存储
        userRedisTemplate.opsForValue().set("user_of_email_" + email, loginUser, 1, TimeUnit.HOURS);
    }
    
    @Override
    public UserDTO getLoginUser() {
        final String email = getLoginUserPrimaryEmail();
        UserDTO loginUser = userRedisTemplate.opsForValue().get("user_of_email_" + email);
        if (loginUser == null) {
            loginUser = loadUser(email);
        }
        return loginUser;
    }
    
    private String getLoginUserPrimaryEmail() {
        final SecurityContext context = SecurityContextHolder.getContext();
        final Authentication auth = context.getAuthentication();
        return (String) auth.getPrincipal();
    }
    
}
