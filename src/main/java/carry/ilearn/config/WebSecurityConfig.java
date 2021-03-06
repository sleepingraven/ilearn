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
     * ????????????
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // ????????????
                .cors()
                .and()
                // ??????????????????????????????
                .csrf()
                .disable()
                // ??????
                .authorizeRequests()
                // ????????????
                .antMatchers("/user/avatar-url", "/user/sign-up")
                .anonymous()
                // ????????????
                .antMatchers(HttpMethod.GET, "/discussion/list", "/discussion/replies/list",
                             "/discussion/replies/sub/list")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/course/overview/**", "/course/preview/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/learn/introduction/**", "/learn/chapter/**", "/learn/courseware/**")
                .permitAll()
                .antMatchers("/exam/from-code/**", "/exam/join/**", "/exam/list/joint/**")
                .permitAll()
                // ????????????
                .antMatchers("/user/**", "/learn/**")
                .authenticated()
                // ????????????
                .antMatchers("/discussion/like", "/notification/**")
                .authenticated()
                // ??????????????????
                .antMatchers("/course/**")
                .hasRole("COURSE_SUPPLIER")
                // ??????????????????
                .antMatchers("/exam/**")
                .hasRole("EXAM_INITIATOR")
                // ????????????
                .antMatchers("/discussion/**")
                .hasRole("SPEAKER")
                // ???????????????
                .antMatchers("/admin/**")
                .hasRole("ADMIN")
                // ?????????????????????????????????
                .anyRequest()
                .permitAll()
                .and()
                // ??????JWT???????????????
                .addFilter(jwtAuthenticationFilter)
                // ??????JWT???????????????
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement()
                // ??????Session?????????????????????Spring Security????????????HttpSession ?????????HttpSession?????????SecurityContext
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // ????????????
                .exceptionHandling()
                //??????????????????
                .accessDeniedHandler(denyHandler)
                // ?????????????????????????????????????????????
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
     * ????????????
     *
     * @return ??????URL?????????????????????
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // ??????????????????
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
