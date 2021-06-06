package carry.ilearn.service.datatransferobject;

import lombok.Data;

import java.util.Date;

/**
 * @author Carry
 * @since 2021/5/23
 */
@Data
public class ExamDTO {
    private Integer id;
    private String title;
    private Date startTime;
    private Integer totalTime;
    private String href;
    private String code;
    
}
