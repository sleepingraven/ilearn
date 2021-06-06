package carry.ilearn.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Carry
 * @since 2021/4/9
 */
@AllArgsConstructor
@Getter
public enum EmBusinessError implements CommonError {
    /**
     * 1000X 通用错误类型
     */
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),
    UNKNOWN_ERROR(10002, "未知错误"),
    /**
     * 2000X 用户信息相关错误定义
     */
    USER_NOT_EXISTS(20001, "用户不存在"),
    USER_LOGIN_FAIL(20002, "用户名或密码不正确"),
    USER_NOT_LOGIN(20003, "用户还未登录"),
    
    ;
    
    private final int errorCode;
    private final String errorMsg;
}
