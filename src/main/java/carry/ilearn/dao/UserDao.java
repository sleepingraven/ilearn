package carry.ilearn.dao;

import carry.ilearn.dataobject.UserDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(Integer id);
    
    int insert(UserDO record);
    
    int insertSelective(UserDO record);
    
    UserDO selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(UserDO record);
    
    int updateByPrimaryKey(UserDO record);
    
    UserDO selectPassportByEmail(@Param("email") String email);
    
    void updateAvatarUrl(@Param("id") Integer id, @Param("avatarUrl") String avatarUrl);
    
    UserDO selectByEmail(@Param("email") String email);
    
    List<UserDO> selectExceptFirstWithRoles();
    
}
