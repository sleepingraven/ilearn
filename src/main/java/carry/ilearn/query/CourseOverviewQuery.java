package carry.ilearn.query;

import lombok.Data;

/**
 * @author Carry
 * @since 2021/5/19
 */
@Data
public class CourseOverviewQuery {
    private Integer id;
    private String title;
    private String notice;
    
    private Integer userId;
    
}
