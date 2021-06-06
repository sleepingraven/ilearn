package carry.ilearn.service.datatransferobject;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/22
 */
@Data
public class PostDTO {
    private Integer id;
    private String title;
    private Integer totalReplies;
    private Integer firstLevelComments;
    private StatementDTO statementDTO;
    
}
