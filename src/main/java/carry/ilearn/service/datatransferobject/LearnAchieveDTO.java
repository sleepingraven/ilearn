package carry.ilearn.service.datatransferobject;

import lombok.Data;

import java.util.Date;

/**
 * @author Carry
 * @since 2021/5/23
 */
@Data
public class LearnAchieveDTO {
    private Date achieveTime;
    
    private CourseDTO courseDTO;
    private CourseSectionDTO sectionDTO;
    
    private Integer finishedTasks;
    private Integer totalTasks;
    
}
