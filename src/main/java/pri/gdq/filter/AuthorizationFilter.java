package pri.gdq.filter;

import pri.gdq.po.UserPO;
import pri.gdq.util.JwtUtil;
import pri.gdq.util.UserUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * program : taxoa-backend
 * description: 权限拦截器
 *
 * @author : gdq
 * data : 2019-11-20 09:48
 **/
public class AuthorizationFilter extends BasicAuthenticationFilter {
    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * description : 拦截请求
     *
     * @param request:http请求体
     * @param response:http响应体
     * @param chain:处理链
     * @author : gdq
     * data : 2020/1/3 0003 18:20
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // TODO:Redis缓存Token
        if ("/token".equals(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            chain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(token));
        chain.doFilter(request, response);
    }

    /**
     * description : 验证身份
     *
     * @param token:信息令牌
     * @return UsernamePasswordAuthenticationToken
     * @author : gdq
     * data : 2020/1/3 0003 18:21
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (!StringUtils.isEmpty(token)) {
            Claims claims = null;
            try {
                claims = JwtUtil.parserJwt(token);
            } catch (ExpiredJwtException e) {
                logger.info("身份过期");
            }
            if (!ObjectUtils.isEmpty(claims)) {
                UserPO userPO = UserUtil.getUser(claims.get("username").toString());
                return new UsernamePasswordAuthenticationToken(userPO, userPO.getPassword(), userPO.getAuthorities());
            }
        }
        return null;
    }
}
