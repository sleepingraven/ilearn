package carry.ilearn.controller.viewobject;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Carry
 * @since 2021/5/17
 */
@Data
public class CourseIntroductionVO {
    private String title;
    
    private Date createTime;
    private Integer userId;
    private String userName;
    
    List<CourseIntroductionItemVO> introductions;
    
}
