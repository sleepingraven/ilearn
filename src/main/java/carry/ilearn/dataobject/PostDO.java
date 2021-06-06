package carry.ilearn.dataobject;

import lombok.Data;

import java.io.Serializable;

/**
 * post
 *
 * @author
 */
@Data
public class PostDO implements Serializable {
    private Integer id;
    
    private String title;
    
    private Integer courseId;
    
    private Integer statementId;
    
    private Integer totalReplies;
    
    private Integer firstLevelComments;
    
    private StatementDO statementDO;
    
    private static final long serialVersionUID = 1L;
    
}
