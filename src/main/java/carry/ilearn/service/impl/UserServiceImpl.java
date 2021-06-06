package carry.ilearn.service.impl;

import carry.ilearn.common.error.BusinessException;
import carry.ilearn.common.error.EmBusinessError;
import carry.ilearn.converter.UserConverter;
import carry.ilearn.dao.EmailDao;
import carry.ilearn.dao.UserDao;
import carry.ilearn.dataobject.EmailDO;
import carry.ilearn.dataobject.UserDO;
import carry.ilearn.query.*;
import carry.ilearn.service.UserService;
import carry.ilearn.service.datatransferobject.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Carry
 * @since 2021/5/14
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private EmailDao emailDao;
    @Resource
    private UserConverter userConverter;
    @Resource
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void doSignUp(SignQuery signQuery) throws BusinessException {
        EmailDO emailDO = new EmailDO();
        try {
            emailDO.setEmail(signQuery.getEmail());
            emailDao.insertSelective(emailDO);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "邮箱已被注册");
        }
        
        UserDO userDO = new UserDO();
        try {
            userDO.setName(signQuery.getName());
            userDO.setMainEmailId(emailDO.getId());
            final String password = signQuery.getPassword();
            final String ecrypted = passwordEncoder.encode(password);
            userDO.setEcryptedPassword(ecrypted.substring(ecrypted.indexOf("}") + 1));
            userDO.setAvatarUrl(signQuery.getAvatarUrl());
            userDao.insertSelective(userDO);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户名已被使用");
        }
        
        emailDO.setUserId(userDO.getId());
        emailDao.updateByPrimaryKeySelective(emailDO);
    }
    
    @Override
    public void updateAvatarUrl(UserAvatarQuery userAvatarQuery) {
        userDao.updateAvatarUrl(userAvatarQuery.getId(), userAvatarQuery.getAvatarUrl());
    }
    
    @Override
    public void updateUser(UserProfileQuery userProfileQuery) {
        userDao.updateByPrimaryKeySelective(userConverter.userProfileQuery2UserDO(userProfileQuery));
    }
    
    @Override
    public List<String> getEmails(Integer userId) {
        final List<EmailDO> emailList = emailDao.selectByUserId(userId);
        return emailList.stream().map(EmailDO::getEmail).collect(Collectors.toList());
    }
    
    @Override
    public void addEmail(UserEmailQuery userEmailQuery) throws BusinessException {
        try {
            EmailDO emailDO = new EmailDO();
            emailDO.setEmail(userEmailQuery.getEmail());
            emailDO.setUserId(userEmailQuery.getId());
            emailDao.insertSelective(emailDO);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "该邮箱已被使用");
        }
    }
    
    @Override
    public UserDTO getUserByEmail(String email) throws BusinessException {
        final EmailDO emailDO = emailDao.selectByEmail(email);
        if (emailDO == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "未找到该用户");
        }
        final UserDO userDO = userDao.selectByPrimaryKey(emailDO.getUserId());
        return userConverter.userDO2UserDTO(userDO);
    }
    
    @Override
    public int updateMainEmail(UserEmailQuery userEmailQuery) throws BusinessException {
        final EmailDO emailDO = emailDao.selectByEmail(userEmailQuery.getEmail());
        if (emailDO == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "邮箱错误");
        }
        if (!Objects.equals(emailDO.getUserId(), userEmailQuery.getId())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "邮箱错误");
        }
        final UserDO userDO = userDao.selectByPrimaryKey(userEmailQuery.getId());
        userDO.setMainEmailId(emailDO.getId());
        userDao.updateByPrimaryKeySelective(userDO);
        return emailDO.getId();
    }
    
    @Override
    public void removeEmail(UserEmailQuery userEmailQuery) throws BusinessException {
        // fixme 并发
        final Integer id = emailDao.selectNonMainIdByEmailAndUserId(userEmailQuery.getEmail(), userEmailQuery.getId());
        if (id == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "主邮箱不能删除");
        }
        emailDao.deleteByPrimaryKey(id);
    }
    
    @Override
    public void changePassword(UserPasswordQuery userPasswordQuery) throws Exception {
        if (!Objects.equals(userPasswordQuery.getTarget(), userPasswordQuery.getConfirm())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "密码不相同");
        }
        final UserDO userDO = userDao.selectByPrimaryKey(userPasswordQuery.getId());
        if (!passwordEncoder.matches(userPasswordQuery.getOrigin(), "{bcrypt}" + userDO.getEcryptedPassword())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "原密码错误");
        }
        
        final String ecrypted = passwordEncoder.encode(userPasswordQuery.getTarget());
        userDO.setEcryptedPassword(ecrypted.substring(ecrypted.indexOf("}") + 1));
        userDao.updateByPrimaryKeySelective(userDO);
    }
    
}
