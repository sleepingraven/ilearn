package carry.ilearn.dataobject;

import java.io.Serializable;
import lombok.Data;

/**
 * course_refer
 * @author 
 */
@Data
public class CourseReferDO implements Serializable {
    private Integer id;

    private String title;

    private String href;

    private Integer courseId;

    private static final long serialVersionUID = 1L;
}