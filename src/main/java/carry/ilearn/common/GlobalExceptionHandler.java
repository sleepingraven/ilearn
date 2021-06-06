package carry.ilearn.common;

import carry.ilearn.common.error.BusinessException;
import carry.ilearn.common.error.CommonError;
import carry.ilearn.common.error.EmBusinessError;
import carry.ilearn.common.response.CommonReturnType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hzllb on 2018/12/22.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonReturnType<?> doError(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        log.error(ex.getMessage(), ex);
        
        CommonError ce = EmBusinessError.UNKNOWN_ERROR;
        if (ex instanceof BusinessException) {
            ce = (BusinessException) ex;
        } else if (ex instanceof ServletRequestBindingException) {
            // 处理 405 状态（缺少参数）
            ce = new BusinessException(EmBusinessError.UNKNOWN_ERROR, "url 绑定路由问题");
        } else if (ex instanceof NoHandlerFoundException) {
            // 处理 404 状态
            ce = new BusinessException(EmBusinessError.UNKNOWN_ERROR, "没有找到对应的访问路径");
        }
        
        return CommonReturnType.withError(ce);
    }
    
}
