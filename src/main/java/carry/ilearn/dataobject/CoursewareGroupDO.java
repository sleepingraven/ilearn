package carry.ilearn.dataobject;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * courseware_group
 *
 * @author
 */
@Data
public class CoursewareGroupDO implements Serializable {
    private Integer id;
    
    private String description;
    
    private Integer courseId;
    
    private List<CoursewareItemDO> coursewareItemDOList;
    
    private static final long serialVersionUID = 1L;
    
}
