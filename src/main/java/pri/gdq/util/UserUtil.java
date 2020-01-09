package pri.gdq.util;

import pri.gdq.po.UserDetailPO;
import pri.gdq.po.UserPO;
import pri.gdq.service.UserDetailService;
import pri.gdq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * program : taxoa-backend
 * description: 用户Util
 *
 * @author : gdq
 * data : 2019-12-10 14:25
 **/
@Component
public class UserUtil {
    private static UserDetailService userDetailService;

    private static UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        UserUtil.userService = userService;
    }

    public static String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static UserDetailPO getUserDetail(String username) {
        return userDetailService.queryUserDetailByUsername(username);
    }

    public static UserDetailPO getUserDetail() {
        return userDetailService.queryUserDetailByUsername(getUserName());
    }

    public static UserPO getUser() {
        return userService.queryUserByUsername(getUserName());
    }

    public static UserPO getUser(String username) {
        return userService.queryUserByUsername(username);
    }

    public static String getName() {
        return getUserDetail().getName();
    }

    @Autowired
    public void setUserService(UserDetailService userDetailService) {
        UserUtil.userDetailService = userDetailService;
    }
}
