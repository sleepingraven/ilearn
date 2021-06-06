package carry.ilearn.dao;

import carry.ilearn.dataobject.LearnAchieveDO;
import carry.ilearn.dataobject.LearnDO;
import carry.ilearn.dataobject.LearnProcessDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LearnDao {
    int deleteByPrimaryKey(Integer id);
    
    int insert(LearnDO record);
    
    int insertSelective(LearnDO record);
    
    LearnDO selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(LearnDO record);
    
    int updateByPrimaryKey(LearnDO record);
    
    boolean checkUserLearn(@Param("sectionId") Integer sectionId, @Param("userId") Integer userId);
    
    int insertUserLearn(@Param("sectionId") Integer sectionId, @Param("userId") Integer userId,
            @Param("status") Integer status);
    
    int updateUserLearn(@Param("sectionId") Integer sectionId, @Param("userId") Integer userId,
            @Param("status") Integer status);
    
    List<LearnAchieveDO> selectUserLearnAchievePaged(@Param("userId") Integer userId, @Param("offset") Integer offset,
            @Param("limit") Integer limit);
    
    Long selectUserLearnAchieveCount(Integer userId);
    
    LearnProcessDO selectLearnProcessDO(@Param("userId") Integer userId, @Param("courseId") Integer courseId);
    
}
