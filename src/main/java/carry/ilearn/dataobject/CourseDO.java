package carry.ilearn.dataobject;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * course
 *
 * @author
 */
@Data
public class CourseDO implements Serializable {
    private Integer id;
    
    private String title;
    
    private Date createTime;
    
    private String previewImgUrl;
    
    private Integer totalLearning;
    
    private Integer userId;
    
    private String notice;
    
    private Boolean banned;
    
    private static final long serialVersionUID = 1L;
    
}
