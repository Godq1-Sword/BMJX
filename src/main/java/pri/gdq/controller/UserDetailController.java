package pri.gdq.controller;

import pri.gdq.entity.ResponseBody;
import pri.gdq.po.UserDetailPO;
import pri.gdq.service.UserDetailService;
import pri.gdq.util.ResponseBodyUtil;
import pri.gdq.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * program : taxoa-backend
 * description: 用户详细信息Controller
 *
 * @author : gdq
 * data : 2019-12-06 17:34
 **/
@RestController
@RequestMapping("/userDetail")
public class UserDetailController {

    // 用户信息Service
    private UserDetailService userDetailService;

    /**
     * description : 加载用户树形图
     *
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 17:55
     */
    @GetMapping("/tree")
    public ResponseBody loadUserDetailTree() {
        return ResponseBodyUtil.generateSucResp(userDetailService.queryUserDetailTree(), "加载人员树成功");
    }

    /**
     * description : 查询当前登录用户信息
     *
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 17:56
     */
    @GetMapping("/user")
    public ResponseBody loadUserDetail() {
        return ResponseBodyUtil.generateSucResp(userDetailService.queryUserDetailByUsername(UserUtil.getUserName()), "加载人员信息成功");
    }

    /**
     * description : 更新用户信息
     *
     * @param username:用户名
     * @param name:姓名
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 18:11
     */
    @PutMapping("/user")
    public ResponseBody updUserDetail(String username, String name) {
        UserDetailPO userDetailPO = userDetailService.queryUserDetailByUsername(username);
        userDetailPO.setName(name);
        System.out.println(userDetailPO);
        return ResponseBodyUtil.generateSucResp(userDetailService.modifyUserDetail(userDetailPO), "更新用户信息成功");
    }

    @Autowired
    public UserDetailController(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

}
