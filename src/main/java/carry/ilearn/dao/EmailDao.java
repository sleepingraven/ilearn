package carry.ilearn.dao;

import carry.ilearn.dataobject.EmailDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmailDao {
    int deleteByPrimaryKey(Integer id);
    
    int insert(EmailDO record);
    
    int insertSelective(EmailDO record);
    
    EmailDO selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(EmailDO record);
    
    int updateByPrimaryKey(EmailDO record);
    
    List<EmailDO> selectByUserId(Integer userId);
    
    EmailDO selectByEmail(String email);
    
    Integer selectNonMainIdByEmailAndUserId(@Param("email") String email, @Param("userId") Integer userId);
    
}
