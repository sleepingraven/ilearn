package carry.ilearn.dao;

import carry.ilearn.dataobject.UserDO;
import carry.ilearn.dataobject.UserReceivedRepliesDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserReceivedRepliesDao {
    int deleteByPrimaryKey(Integer id);
    
    int insert(UserReceivedRepliesDO record);
    
    int insertSelective(UserReceivedRepliesDO record);
    
    int updateByPrimaryKeySelective(UserReceivedRepliesDO record);
    
    int updateByPrimaryKey(UserReceivedRepliesDO record);
    
    List<UserReceivedRepliesDO> selectByUserToIdPaged(@Param("userToId") Integer userToId,
            @Param("beginId") Integer beginId, @Param("offset") Integer offset, @Param("limit") Integer limit);
    
    UserDO selectFromUser(Integer commentFromId);
    
    Integer selectReplyReceivedSnapshotBeginId(Integer userId);
    
    /**
     * todo 删除优化
     */
    int deleteUnread(@Param("userId") Integer userId, @Param("beginId") Integer beginId);
    
}
