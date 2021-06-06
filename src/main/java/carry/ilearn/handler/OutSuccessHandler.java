package carry.ilearn.handler;

import carry.ilearn.common.response.CommonReturnType;
import carry.ilearn.service.UserStateService;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Carry
 * @since 2021/5/27
 */
@Component
public class OutSuccessHandler implements LogoutSuccessHandler {
    @Resource
    private UserStateService userStateService;
    
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Authentication authentication) throws IOException, ServletException {
        // 设置响应头
        httpServletResponse.setContentType("application/json; charset=utf-8");
        // 返回值
        httpServletResponse.getWriter().write(JSON.toJSONString(CommonReturnType.create("退出登录成功")));
    }
    
}
