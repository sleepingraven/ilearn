package carry.ilearn.dataobject;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * section
 *
 * @author
 */
@Data
public class SectionDO implements Serializable {
    private Integer id;
    
    private String title;
    
    private String videoUrl;
    
    private Integer seconds;
    
    private String posterFileName;
    
    private String posterUrl;
    
    private Integer chapterId;
    
    private Integer status;
    private Date achieveTime;
    
    private static final long serialVersionUID = 1L;
    
}
