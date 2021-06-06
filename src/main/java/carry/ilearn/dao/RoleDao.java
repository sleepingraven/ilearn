package carry.ilearn.dao;

import carry.ilearn.dataobject.RoleDO;

import java.util.List;

public interface RoleDao {
    
    List<RoleDO> selectRolesByUserId(Integer userId);
    
    List<RoleDO> selectAll();
    
}
