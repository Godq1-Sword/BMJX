package pri.gdq.service;

import pri.gdq.po.UserPO;

import java.util.List;

/**
 * program : taxoa-backend
 * description: 用户Service
 *
 * @author : gdq
 * data : 2019-11-19 16:21
 **/
public interface UserService {
    /**
     * description : 查询所有用户信息
     *
     * @return List
     * @author : gdq
     * data : 2020/1/9 0009 10:44
     */
    List<UserPO> queryUserList();

    /**
     * description : 根据用户名查询用户信息
     *
     * @param username:用户名
     * @return UserPO
     * @author : gdq
     * data : 2020/1/9 0009 10:44
     */
    UserPO queryUserByUsername(String username);
}
