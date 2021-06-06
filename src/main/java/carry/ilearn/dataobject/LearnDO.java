package carry.ilearn.dataobject;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * learn
 *
 * @author
 */
@Data
public class LearnDO implements Serializable {
    private Integer id;
    
    /**
     * 0：未学习；1：已开始；2：已完成
     */
    private Integer status;
    
    private Date achieveTime;
    
    private Integer userId;
    
    private Integer sectionId;
    
    private static final long serialVersionUID = 1L;
    
}
