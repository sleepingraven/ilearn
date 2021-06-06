package carry.ilearn.dataobject;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * chapter
 *
 * @author
 */
@Data
public class ChapterDO implements Serializable {
    private Integer id;
    
    private String title;
    
    private String description;
    
    private Integer courseId;
    
    private List<SectionDO> sectionDOList;
    
    private static final long serialVersionUID = 1L;
    
}
