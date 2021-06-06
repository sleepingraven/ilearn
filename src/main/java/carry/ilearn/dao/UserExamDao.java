package carry.ilearn.dao;

import carry.ilearn.dataobject.UserExamDO;

public interface UserExamDao {
    int deleteByPrimaryKey(Integer id);
    
    int insert(UserExamDO record);
    
    int insertSelective(UserExamDO record);
    
    UserExamDO selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(UserExamDO record);
    
    int updateByPrimaryKey(UserExamDO record);
    
}
