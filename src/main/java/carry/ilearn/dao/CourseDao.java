package carry.ilearn.dao;

import carry.ilearn.dataobject.CourseDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseDao {
    int deleteByPrimaryKey(Integer id);
    
    int insert(CourseDO record);
    
    int insertSelective(CourseDO record);
    
    @Deprecated
    CourseDO selectByPrimaryKey(Integer id);
    
    CourseDO selectByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);
    
    boolean checkByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);
    
    int updateByPrimaryKeySelective(CourseDO record);
    
    int updateByPrimaryKey(CourseDO record);
    
    List<CourseDO> selectByUserId(Integer userId);
    
    int updateBannedByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId,
            @Param("banned") Boolean banned);
    
    List<CourseDO> selectAllPaged(@Param("offset") Integer offset, @Param("limit") Integer limit);
    
    /**
     * todo count 优化
     */
    long selectTotalNum();
    
}
