package pri.gdq.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * program : taxoa-backend
 * description: 用户名密码拦截器
 *
 * @author : gdq
 * data : 2019-11-19 18:19
 **/
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return null;
    }

    /**
     * description : 验证成功处理
     *
     * @param request:http请求体
     * @param response:http响应体
     * @param chain:处理链
     * @param authResult:用户过滤信息
     * @author : gdq
     * data : 2020/1/3 0003 18:17
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
    }

    /**
     * description : 验证失败处理
     *
     * @param request:http请求体
     * @param response:http响应体
     * @param failed:过滤失败信息
     * @author : gdq
     * data : 2020/1/3 0003 18:19
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.getWriter().write("authentication failed, reason: " + failed.getMessage());
    }
}
