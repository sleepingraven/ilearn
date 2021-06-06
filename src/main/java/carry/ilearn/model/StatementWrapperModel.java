package carry.ilearn.model;

import lombok.Data;

import java.util.List;

/**
 * @author Carry
 * @since 2021/5/23
 */
@Data
public class StatementWrapperModel<T> {
    private List<T> wrapperList;
    private List<Integer> likedStatementIdList;
    
}
