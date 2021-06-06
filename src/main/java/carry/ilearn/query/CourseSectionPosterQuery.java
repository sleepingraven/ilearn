package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/20
 */
@Data
public class CourseSectionPosterQuery {
    private Integer id;
    private String posterFileName;
    private String posterUrl;
    
    private Integer chapterId;
    private Integer userId;
    
}
