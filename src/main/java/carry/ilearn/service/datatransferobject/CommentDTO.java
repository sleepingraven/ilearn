package carry.ilearn.service.datatransferobject;

import lombok.Data;

import java.util.List;

/**
 * @author Carry
 * @since 2021/5/22
 */
@Data
public class CommentDTO {
    private Integer id;
    /**
     * 第几级评论。1，2或3以上的数
     */
    private Integer level;
    private Integer replyToId;
    
    private StatementDTO statementDTO;
    private List<CommentDTO> commentDTOList;
    private Long count;
    
}
