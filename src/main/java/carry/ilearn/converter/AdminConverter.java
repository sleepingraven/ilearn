package carry.ilearn.converter;

import carry.ilearn.dataobject.RoleDO;
import carry.ilearn.dataobject.UserDO;
import carry.ilearn.model.RoleModel;
import carry.ilearn.model.UserWithRoleModel;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Carry
 * @since 2021/5/28
 */
@Mapper(componentModel = "spring")
public interface AdminConverter {
    
    RoleModel roleDO2RoleModel(RoleDO roleDO);
    
    List<RoleModel> listRoleDO2RoleModel(List<RoleDO> roleDOList);
    
    UserWithRoleModel userDO2UserWithRoleModel(UserDO userDO);
    
    List<UserWithRoleModel> listUserDO2UserWithRoleModel(List<UserDO> userDOList);
    
}
