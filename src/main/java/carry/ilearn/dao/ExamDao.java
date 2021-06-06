package carry.ilearn.dao;

import carry.ilearn.dataobject.ExamDO;

import java.util.List;

public interface ExamDao {
    int deleteByPrimaryKey(Integer id);
    
    int insert(ExamDO record);
    
    int insertSelective(ExamDO record);
    
    ExamDO selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(ExamDO record);
    
    int updateByPrimaryKey(ExamDO record);
    
    List<ExamDO> selectByUserId(Integer userId);
    
    ExamDO selectByCode(String code);
    
    List<ExamDO> selectJointByUserId(Integer userId);
    
}
