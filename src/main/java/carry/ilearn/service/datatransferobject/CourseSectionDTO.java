package carry.ilearn.service.datatransferobject;

import lombok.Data;

import java.util.Date;

/**
 * @author Carry
 * @since 2021/5/19
 */
@Data
public class CourseSectionDTO {
    private Integer id;
    private String title;
    private String videoUrl;
    private Integer seconds;
    private String posterFileName;
    private String posterUrl;
    private Integer chapterId;
    
    private Integer status;
    private Date achieveTime;
    
}
