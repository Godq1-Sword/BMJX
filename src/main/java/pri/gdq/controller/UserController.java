package pri.gdq.controller;

import pri.gdq.entity.ResponseBody;
import pri.gdq.po.UserPO;
import pri.gdq.service.impl.UserServiceImpl;
import pri.gdq.util.JwtUtil;
import pri.gdq.util.ResponseBodyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * program :] taxoa-backend
 * description: 用户信息Controller
 *
 * @author : gdq
 * data : 2019-11-14 15:48
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    // 账户Service
    private UserServiceImpl userService;

    /**
     * description : 查询用户否存在
     *
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 17:55
     */
    @GetMapping
    public ResponseBody getUserList() {
        List<UserPO> userPOList = userService.queryUserList();
        if (!ObjectUtils.isEmpty(userPOList)) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("userPoList", userPOList);
            return ResponseBodyUtil.generateSucResp(JwtUtil.generateJWT(claims), "生成JWT成功");
        }
        return ResponseBodyUtil.generatePsEResp(null);
    }

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }
}
