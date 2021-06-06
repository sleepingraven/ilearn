package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/23
 */
@Data
public class ExamPageQuery {
    private Integer pageIdx;
    private Integer pageSize;
    private Integer userId;
    
}
