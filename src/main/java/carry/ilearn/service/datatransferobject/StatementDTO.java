package carry.ilearn.service.datatransferobject;

import lombok.Data;

import java.util.Date;

/**
 * @author Carry
 * @since 2021/5/22
 */
@Data
public class StatementDTO {
    private Integer id;
    private Date createTime;
    private String content;
    private UserDTO user;
    private Integer totalLikes;
    
}
