package carry.ilearn.dataobject;

import lombok.Data;

import java.io.Serializable;

/**
 * course_introduction
 *
 * @author
 */
@Data
public class CourseIntroductionDO implements Serializable {
    private Integer id;
    
    private String title;
    
    private String content;
    
    private Integer courseId;
    
    private static final long serialVersionUID = 1L;
    
}
