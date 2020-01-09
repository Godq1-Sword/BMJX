package pri.gdq.filter;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * program : taxoa-backend
 * description: 日志管理
 *
 * @author : gdq
 * data : 2019-12-31 15:45
 **/
@Aspect
@Component
public class WebLogAspect {
    // 日志
    private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    /**
     * description : 切面
     *
     * @author : gdq
     * data : 2020/1/3 0003 18:22
     */
    @Pointcut("execution(public * com.gdq.controller..*.*(..))")
    public void pointCut() {
    }

    /**
     * description : 前置环绕
     *
     * @author : gdq
     * data : 2020/1/3 0003 18:22
     */
    @Before("pointCut()")
    public void logBeforeRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtils.isEmpty(attributes)) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        // 记录请求
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            logger.info("name:{},value:{}", name, request.getParameter(name));
        }
    }

    /**
     * description : 后置环绕
     *
     * @author : gdq
     * data : 2020/1/3 0003 18:22
     */
    @AfterReturning(returning = "ret", pointcut = "pointCut()")
    public void doAfterReturning(Object ret) {
        // 记录请求结果
        logger.info("RESPONSE : " + ret);
    }
}