package carry.ilearn.handler;

import carry.ilearn.common.response.CommonReturnType;
import com.alibaba.fastjson.JSON;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Carry
 * @since 2021/5/27
 */
@Component
public class DenyHandler implements AccessDeniedHandler {
    
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            AccessDeniedException e) throws IOException, ServletException {
        // 设置响应头
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // 返回值
        httpServletResponse.getWriter().write(JSON.toJSONString(CommonReturnType.withError(e.getMessage())));
    }
    
}
