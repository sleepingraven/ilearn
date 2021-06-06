package carry.ilearn.controller.viewobject;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/22
 */
@Data
public class PostVO {
    private Integer id;
    private String title;
    private Integer totalReplies;
    private Integer firstLevelComments;
    private StatementVO statement;
    
}
