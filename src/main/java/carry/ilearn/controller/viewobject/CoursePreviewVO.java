package carry.ilearn.controller.viewobject;

import lombok.Data;

/**
 * 用于首页课程显示
 *
 * @author Carry
 * @since 2021/5/17
 */
@Data
public class CoursePreviewVO {
    private Integer id;
    private String title;
    private String previewImgUrl;
    private Integer totalLearning;
    private Boolean banned;
    
}
