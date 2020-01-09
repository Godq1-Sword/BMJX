package pri.gdq.controller;

import pri.gdq.entity.ResponseBody;
import pri.gdq.po.UserPO;
import pri.gdq.service.impl.UserServiceImpl;
import pri.gdq.util.JwtUtil;
import pri.gdq.util.ResponseBodyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: TokenController
 *
 * @author : gdq
 * data : 2019-11-20 16:06
 **/
@RestController
@RequestMapping("/token")
public class TokenController {
    // 账户service
    private UserServiceImpl userService;

    /**
     * description : 登录
     *
     * @param username:用户名
     * @param password:密码
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 17:54
     */
    @PostMapping
    public ResponseBody login(@RequestParam String username, @RequestParam String password) {
        UserPO userPO = (UserPO) userService.loadUserByUsername(username);
        if (!ObjectUtils.isEmpty(userPO) && userPO.getPassword().equals(password)) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", username);
            claims.put("password", password);
            return ResponseBodyUtil.generateSucResp(JwtUtil.generateJWT(claims), "生成JWT成功");
        }
        return ResponseBodyUtil.generatePsEResp(null);
    }

    @Autowired
    public TokenController(UserServiceImpl userService) {
        this.userService = userService;
    }

}
