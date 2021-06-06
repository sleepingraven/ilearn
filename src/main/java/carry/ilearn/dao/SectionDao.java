package carry.ilearn.dao;

import carry.ilearn.dataobject.SectionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SectionDao {
    int deleteByPrimaryKey(Integer id);
    
    int insert(SectionDO record);
    
    int insertSelective(SectionDO record);
    
    SectionDO selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(SectionDO record);
    
    int updateByPrimaryKey(SectionDO record);
    
    List<SectionDO> selectByChapterId(Integer chapterId);
    
    List<SectionDO> selectByChapterIdWithLearn(@Param("chapterId") Integer chapterId, @Param("userId") Integer userId);
    
    int deleteByChapterId(Integer chapterId);
    
    int selectCourseIdBySectionId(Integer sectionId);
    
}
