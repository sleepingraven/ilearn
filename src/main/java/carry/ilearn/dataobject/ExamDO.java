package carry.ilearn.dataobject;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * exam
 * @author 
 */
@Data
public class ExamDO implements Serializable {
    private Integer id;

    private String title;

    private Date startTime;

    private Integer totalTime;

    private String href;

    private Integer userId;

    private String code;

    private static final long serialVersionUID = 1L;
}