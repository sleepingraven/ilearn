package carry.ilearn.dao;

import carry.ilearn.dataobject.CoursewareItemDO;

import java.util.List;

public interface CoursewareItemDao {
    int deleteByPrimaryKey(Integer id);
    
    int insert(CoursewareItemDO record);
    
    int insertSelective(CoursewareItemDO record);
    
    CoursewareItemDO selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(CoursewareItemDO record);
    
    int updateByPrimaryKey(CoursewareItemDO record);
    
    List<CoursewareItemDO> selectByGroupId(Integer groupId);
    
}
