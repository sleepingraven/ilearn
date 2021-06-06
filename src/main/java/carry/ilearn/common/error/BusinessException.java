package carry.ilearn.common.error;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * 包装器业务异常类实现
 *
 * @author Carry
 * @since 2021/4/9
 */
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends Exception implements CommonError {
    private final int errorCode;
    private final String errorMsg;
    
    /**
     * 直接接收 EmBusinessError 的传参用于构造业务异常
     */
    public BusinessException(CommonError commonError) {
        this(commonError.getErrorCode(), commonError.getErrorMsg());
    }
    
    /**
     * 接收自定义 errorMsg 的方式构造业务异常
     */
    public BusinessException(CommonError commonError, String errorMsg) {
        this(commonError.getErrorCode(), errorMsg);
    }
    
}
