package carry.ilearn.service.datatransferobject;

import lombok.Data;

import java.io.Serializable;

/**
 * course_introduction
 *
 * @author
 */
@Data
public class CourseIntroductionItemDTO implements Serializable {
    private Integer id;
    private String title;
    private String content;
    
}
