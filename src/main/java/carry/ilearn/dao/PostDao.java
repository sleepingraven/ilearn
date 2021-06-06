package carry.ilearn.dao;

import carry.ilearn.dataobject.PostDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostDao {
    int deleteByPrimaryKey(Integer id);
    
    int insert(PostDO record);
    
    int insertSelective(PostDO record);
    
    PostDO selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(PostDO record);
    
    int updateByPrimaryKey(PostDO record);
    
    List<PostDO> selectByCourseIdPaged(@Param("courseId") Integer courseId, @Param("offset") Integer offset,
            @Param("limit") Integer limit);
    
    int increaseReply(Integer id);
    
    int increaseFirstLevelReply(Integer id);
    
}
