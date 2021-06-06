package carry.ilearn.dao;

import carry.ilearn.dataobject.UserRoleDO;
import org.apache.ibatis.annotations.Param;

public interface UserRoleDao {
    int deleteByPrimaryKey(Integer id);
    
    int insert(UserRoleDO record);
    
    int insertSelective(UserRoleDO record);
    
    UserRoleDO selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(UserRoleDO record);
    
    int updateByPrimaryKey(UserRoleDO record);
    
    int deleteByUserRoles(Integer userId);
    
    int insertUserRoles(@Param("userId") Integer userId, @Param("ids") Integer[] ids);
    
}
