package carry.ilearn.query;

import lombok.Data;

import java.util.Date;

/**
 * @author Carry
 * @since 2021/5/23
 */
@Data
public class ExamAddQuery {
    private String title;
    private Date startTime;
    private Integer totalTime;
    private String href;
    private Integer userId;
    
}
