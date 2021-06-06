package carry.ilearn.controller.viewobject;

import lombok.Data;

import java.util.List;

/**
 * 控制台界面的「课程总览」
 *
 * @author Carry
 * @since 2021/5/17
 */
@Data
public class CourseOverviewVO {
    private Integer id;
    private String previewImgUrl;
    
    private String notice;
    List<CourseReferVO> refers;
    
}
