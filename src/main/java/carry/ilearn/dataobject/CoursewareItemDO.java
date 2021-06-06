package carry.ilearn.dataobject;

import lombok.Data;

import java.io.Serializable;

/**
 * courseware_item
 *
 * @author
 */
@Data
public class CoursewareItemDO implements Serializable {
    private Integer id;
    
    private String title;
    
    private String url;
    
    private Integer groupId;
    
    private static final long serialVersionUID = 1L;
    
}
