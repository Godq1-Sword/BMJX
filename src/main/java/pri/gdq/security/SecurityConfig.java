package pri.gdq.security;

import pri.gdq.filter.AuthorizationFilter;
import pri.gdq.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * program : taxoa-backend
 * description: Spring Security 配置类
 *
 * @author : gdq
 * data : 2019-11-19 16:05
 **/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 用户信息Service
    private UserServiceImpl userDetailsService;

    // 密码编码
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * description : Security配置
     *
     * @param http:请求
     * @author : gdq
     * data : 2020/1/3 0003 18:46
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 跨域
        http.csrf().disable()
                .authorizeRequests()
                // 公开页面
                .antMatchers("/token", "/file/**").permitAll()
                .anyRequest().authenticated().and()
                // 关闭Session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 权限过滤
                .addFilter(new AuthorizationFilter(authenticationManager()));
    }

    /**
     * description : 加密密码
     *
     * @param auth:用户信息
     * @author : gdq
     * data : 2020/1/3 0003 18:47
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Autowired
    public void setUserDetailsService(UserServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
