package carry.ilearn.dao;

import carry.ilearn.dataobject.CourseIntroductionDO;

import java.util.List;

public interface CourseIntroductionDao {
    int deleteByPrimaryKey(Integer id);
    
    int insert(CourseIntroductionDO record);
    
    int insertSelective(CourseIntroductionDO record);
    
    CourseIntroductionDO selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(CourseIntroductionDO record);
    
    int updateByPrimaryKey(CourseIntroductionDO record);
    
    List<CourseIntroductionDO> selectByCourseId(Integer courseId);
    
}
