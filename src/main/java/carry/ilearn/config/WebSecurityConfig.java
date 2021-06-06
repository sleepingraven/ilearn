package carry.ilearn.config;

import carry.ilearn.filter.JWTAuthenticationFilter;
import carry.ilearn.filter.JWTAuthorizationFilter;
import carry.ilearn.handler.DenyHandler;
import carry.ilearn.handler.JWTAuthenticationEntryPoint;
import carry.ilearn.handler.OutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;

/**
 * @author Carry
 * @since 2021/5/27
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    @Resource
    private JWTAuthenticationFilter jwtAuthenticationFilter;
    @Resource
    private DenyHandler denyHandler;
    @Resource
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Resource
    private OutSuccessHandler outSuccessHandler;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    
    /**
     * 安全配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 跨域共享
                .cors()
                .and()
                // 跨域伪造请求限制无效
                .csrf()
                .disable()
                // 授权
                .authorizeRequests()
                // 匿名权限
                .antMatchers("/user/avatar-url", "/user/sign-up")
                .anonymous()
                // 需要放行
                .antMatchers(HttpMethod.GET, "/discussion/list", "/discussion/replies/list",
                             "/discussion/replies/sub/list")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/course/overview/**", "/course/preview/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/learn/introduction/**", "/learn/chapter/**", "/learn/courseware/**")
                .permitAll()
                .antMatchers("/exam/from-code/**", "/exam/join/**", "/exam/list/joint/**")
                .permitAll()
                // 认证权限
                .antMatchers("/user/**", "/learn/**")
                .authenticated()
                // 认证权限
                .antMatchers("/discussion/like", "/notification/**")
                .authenticated()
                // 发布课程权限
                .antMatchers("/course/**")
                .hasRole("COURSE_SUPPLIER")
                // 发起考试权限
                .antMatchers("/exam/**")
                .hasRole("EXAM_INITIATOR")
                // 发言权限
                .antMatchers("/discussion/**")
                .hasRole("SPEAKER")
                // 管理员权限
                .antMatchers("/admin/**")
                .hasRole("ADMIN")
                // 其余资源任何人都可访问
                .anyRequest()
                .permitAll()
                .and()
                // 添加JWT登录拦截器
                .addFilter(jwtAuthenticationFilter)
                // 添加JWT鉴权拦截器
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement()
                // 设置Session的创建策略为：Spring Security永不创建HttpSession 不使用HttpSession来获取SecurityContext
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 异常处理
                .exceptionHandling()
                //授权异常处理
                .accessDeniedHandler(denyHandler)
                // 匿名用户访问无权限资源时的异常
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .logout()
                .logoutUrl("/user/sign-out")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(outSuccessHandler);
    }
    
    /**
     * 跨域配置
     *
     * @return 基于URL的跨域配置信息
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 注册跨域配置
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    
}
