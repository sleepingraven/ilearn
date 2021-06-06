package carry.ilearn.service.impl;

import carry.ilearn.dao.UserDao;
import carry.ilearn.dataobject.RoleDO;
import carry.ilearn.dataobject.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用于加载用户信息
 *
 * @author Carry
 * @since 2021/5/26
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserDao userDao;
    
    /**
     * 登录验证时，通过username获取用户的所有权限信息； 正式环境中就是查询用户数据授权
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("------用户{}身份认证-----", username);
        if (username == null || "" .equals(username)) {
            throw new RuntimeException("用户不能为空");
        }
        // 调用方法查询用户
        UserDO userDO = userDao.selectPassportByEmail(username);
        if (userDO == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<SimpleGrantedAuthority> authorities;
        if (userDO.getRoles() != null) {
            authorities = userDO.getRoles()
                                .stream()
                                .map(RoleDO::getName)
                                .map(name -> new SimpleGrantedAuthority("ROLE_" + name))
                                .collect(Collectors.toList());
        } else {
            authorities = new ArrayList<>();
        }
        
        return new org.springframework.security.core.userdetails.User(username,
                                                                      "{bcrypt}" + userDO.getEcryptedPassword(),
                                                                      authorities);
    }
    
}
