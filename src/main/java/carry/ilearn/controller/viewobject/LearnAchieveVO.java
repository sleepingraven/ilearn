package carry.ilearn.controller.viewobject;

import lombok.Data;

import java.util.Date;

/**
 * @author Carry
 * @since 2021/5/23
 */
@Data
public class LearnAchieveVO {
    private Integer courseId;
    private String courseTitle;
    private String previewImgUrl;
    
    private String sectionTitle;
    private Date achieveTime;
    
    private Integer finishedTasks;
    private Integer totalTasks;
    
}
