package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/20
 */
@Data
public class CourseSectionVideoQuery {
    private Integer id;
    private String videoUrl;
    private Integer seconds;
    
    private Integer chapterId;
    private Integer userId;
    
}
