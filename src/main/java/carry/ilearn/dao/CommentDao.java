package carry.ilearn.dao;

import carry.ilearn.dataobject.CommentDO;
import carry.ilearn.dataobject.CommentFirstLevelDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentDao {
    int deleteByPrimaryKey(Integer id);
    
    int insert(CommentDO record);
    
    int insertSelective(CommentDO record);
    
    CommentDO selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(CommentDO record);
    
    int updateByPrimaryKey(CommentDO record);
    
    List<CommentFirstLevelDO> selectFirstLevelByPostIdPaged(@Param("postId") Integer postId,
            @Param("offset") Integer offset, @Param("limit") Integer limit, @Param("secondOffset") Integer secondOffset,
            @Param("secondLimit") Integer secondLimit);
    
    List<CommentDO> selectSecondLevelByCommentIdPaged(@Param("commentId") Integer commentId,
            @Param("offset") Integer offset, @Param("limit") Integer limit);
    
    long selectSecondLevelCountByCommentId(Integer commentId);
    
}
