package carry.ilearn.service.impl;

import carry.ilearn.converter.AdminConverter;
import carry.ilearn.dao.RoleDao;
import carry.ilearn.dao.UserDao;
import carry.ilearn.dao.UserRoleDao;
import carry.ilearn.dataobject.RoleDO;
import carry.ilearn.dataobject.UserDO;
import carry.ilearn.model.RoleModel;
import carry.ilearn.model.UserRoleModel;
import carry.ilearn.model.UserWithRoleModel;
import carry.ilearn.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Carry
 * @since 2021/5/28
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private UserDao userDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private AdminConverter adminConverter;
    @Resource
    private UserRoleDao userRoleDao;
    
    @Override
    public UserRoleModel getUsers() {
        final List<UserDO> userDOS = userDao.selectExceptFirstWithRoles();
        final List<RoleDO> roleDOS = roleDao.selectAll();
        
        final List<UserWithRoleModel> userWithRoleModels = adminConverter.listUserDO2UserWithRoleModel(userDOS);
        final List<RoleModel> roleModels = adminConverter.listRoleDO2RoleModel(roleDOS);
        final UserRoleModel userRoleModel = new UserRoleModel();
        userRoleModel.setUsers(userWithRoleModels);
        userRoleModel.setAllRoles(roleModels);
        return userRoleModel;
    }
    
    @Override
    public void updateUserRole(Integer userId, Integer[] ids) {
        userRoleDao.deleteByUserRoles(userId);
        if (ids != null) {
            userRoleDao.insertUserRoles(userId, ids);
        }
    }
    
}
