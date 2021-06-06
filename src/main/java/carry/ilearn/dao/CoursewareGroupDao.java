package carry.ilearn.dao;

import carry.ilearn.dataobject.CoursewareGroupDO;

import java.util.List;

public interface CoursewareGroupDao {
    int deleteByPrimaryKey(Integer id);
    
    int insert(CoursewareGroupDO record);
    
    int insertSelective(CoursewareGroupDO record);
    
    CoursewareGroupDO selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(CoursewareGroupDO record);
    
    int updateByPrimaryKey(CoursewareGroupDO record);
    
    List<CoursewareGroupDO> selectByCourseId(Integer courseId);
    
}
