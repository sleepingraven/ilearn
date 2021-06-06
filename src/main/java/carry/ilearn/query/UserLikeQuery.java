package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/22
 */
@Data
public class UserLikeQuery {
    private Integer userId;
    private Integer statementId;
    private Boolean like;
    
}
