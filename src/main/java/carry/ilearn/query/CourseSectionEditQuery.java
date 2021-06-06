package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/19
 */
@Data
public class CourseSectionEditQuery {
    private Integer id;
    private String title;
    
    private Integer chapterId;
    private Integer userId;
    
}
