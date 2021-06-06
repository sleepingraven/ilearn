package carry.ilearn.controller.viewobject;

import lombok.Data;

import java.util.Date;

/**
 * @author Carry
 * @since 2021/5/19
 */
@Data
public class CourseSectionVO {
    private Integer id;
    private String title;
    private String videoUrl;
    private Integer seconds;
    private String posterFileName;
    private String posterUrl;
    
    private Integer status;
    private Date achieveTime;
    
}
