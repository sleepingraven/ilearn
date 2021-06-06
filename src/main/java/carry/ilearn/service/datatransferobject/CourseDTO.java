package carry.ilearn.service.datatransferobject;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Carry
 * @since 2021/5/17
 */
@Data
public class CourseDTO {
    private Integer id;
    private String title;
    private Date createTime;
    private String previewImgUrl;
    private Integer totalLearning;
    private String notice;
    private Boolean banned;
    
    private UserDTO userDTO;
    private List<CourseIntroductionItemDTO> introductions;
    
}
