package carry.ilearn.dao;

import carry.ilearn.dataobject.ChapterDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao {
    int deleteByPrimaryKey(Integer id);
    
    int insert(ChapterDO record);
    
    int insertSelective(ChapterDO record);
    
    ChapterDO selectByPrimaryKey(Integer id);
    
    Integer checkByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);
    
    int updateByPrimaryKeySelective(ChapterDO record);
    
    int updateByPrimaryKey(ChapterDO record);
    
    List<ChapterDO> selectByCourseId(Integer courseId);
    
    List<ChapterDO> selectByCourseIdWithLearn(@Param("courseId") Integer courseId, @Param("userId") Integer userId);
    
}
