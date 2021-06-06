package carry.ilearn.handler;

import carry.ilearn.common.response.CommonReturnType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用于进行匿名用户访问资源时无权限的处理
 *
 * @author Carry
 * @since 2021/5/26
 */
@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        final String reason = authException.getMessage();
        response.getWriter().write(new ObjectMapper().writeValueAsString(CommonReturnType.withError(reason)));
    }
    
}
