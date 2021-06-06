package carry.ilearn.dao;

import carry.ilearn.dataobject.UserLikeDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserLikeDao {
    int deleteByPrimaryKey(Integer id);
    
    int insert(UserLikeDO record);
    
    int insertSelective(UserLikeDO record);
    
    UserLikeDO selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(UserLikeDO record);
    
    int updateByPrimaryKey(UserLikeDO record);
    
    int deleteByUserIdAndStatementId(@Param("userId") Integer userId, @Param("statementId") Integer statementId);
    
    /**
     * 如果 statementIds 的长度为 0，做其他处理
     */
    List<Integer> selectByUserIdAndStatementIdList(@Param("userId") Integer userId,
            @Param("statementIds") List<Integer> statementIds);
    
}
