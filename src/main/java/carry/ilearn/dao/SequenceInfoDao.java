package carry.ilearn.dao;

import carry.ilearn.dataobject.SequenceInfoDO;

public interface SequenceInfoDao {
    int deleteByPrimaryKey(String name);
    
    int insert(SequenceInfoDO record);
    
    int insertSelective(SequenceInfoDO record);
    
    SequenceInfoDO selectByPrimaryKey(String name);
    
    int updateByPrimaryKeySelective(SequenceInfoDO record);
    
    int updateByPrimaryKey(SequenceInfoDO record);
    
    SequenceInfoDO getSequenceByName(String name);
    
}
