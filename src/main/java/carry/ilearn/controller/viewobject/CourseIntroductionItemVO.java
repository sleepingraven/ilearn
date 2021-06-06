package carry.ilearn.controller.viewobject;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 10132
 */
@Data
public class CourseIntroductionItemVO implements Serializable {
    private Integer id;
    private String title;
    private String content;
    
}
