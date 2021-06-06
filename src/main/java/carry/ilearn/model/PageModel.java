package carry.ilearn.model;

import lombok.Data;

import java.util.List;

/**
 * @author Carry
 * @since 2021/5/17
 */
@Data
public class PageModel<T> {
    private Long totalNum;
    private List<T> data;
    
}
