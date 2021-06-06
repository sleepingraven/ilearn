package carry.ilearn.dao;

import carry.ilearn.dataobject.CourseReferDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseReferDao {
    int deleteByPrimaryKey(Integer id);
    
    int insert(CourseReferDO record);
    
    int insertSelective(CourseReferDO record);
    
    CourseReferDO selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(CourseReferDO record);
    
    int updateByPrimaryKey(CourseReferDO record);
    
    List<CourseReferDO> selectByCourseId(Integer courseId);
    
    int deleteByUser(@Param("id") Integer id, @Param("userId") Integer userId);
    
}
