package carry.ilearn.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * comment
 *
 * @author
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentFirstLevelDO extends CommentDO implements Serializable {
    
    private List<CommentDO> commentDOList;
    private Long count;
    
}
