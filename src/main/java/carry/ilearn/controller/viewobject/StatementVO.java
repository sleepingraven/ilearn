package carry.ilearn.controller.viewobject;

import lombok.Data;

import java.util.Date;

/**
 * @author Carry
 * @since 2021/5/22
 */
@Data
public class StatementVO {
    private Integer id;
    private Date createTime;
    private String content;
    private Integer userId;
    private String userName;
    private String avatarUrl;
    private Integer totalLikes;
    
}
