package carry.ilearn.dataobject;

import lombok.Data;

import java.io.Serializable;

/**
 * user_exam
 *
 * @author
 */
@Data
public class UserExamDO implements Serializable {
    private Integer id;
    
    private Integer userId;
    
    private Integer examId;
    
    private static final long serialVersionUID = 1L;
    
}
