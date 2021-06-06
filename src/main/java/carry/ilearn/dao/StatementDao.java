package carry.ilearn.dao;

import carry.ilearn.dataobject.StatementDO;

public interface StatementDao {
    int deleteByPrimaryKey(Integer id);
    
    int insert(StatementDO record);
    
    int insertSelective(StatementDO record);
    
    StatementDO selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(StatementDO record);
    
    int updateByPrimaryKey(StatementDO record);
    
}
