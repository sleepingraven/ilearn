package carry.ilearn.controller.viewobject;

import lombok.Data;

import java.util.List;

/**
 * @author Carry
 * @since 2021/5/19
 */
@Data
public class CourseChapterVO {
    private Integer id;
    private String title;
    private String description;
    
    private Integer courseId;
    private List<CourseSectionVO> sections;
    
}
