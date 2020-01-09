package pri.gdq.service.impl;

import pri.gdq.dao.UserDao;
import pri.gdq.po.UserPO;
import pri.gdq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * program : taxoa-backend
 * description: 用户角色Service层
 *
 * @author : gdq
 * data : 2019-11-19 16:16
 **/
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private UserDao userDao;

    /**
     * description : 根据用户名查找用户信息和角色
     *
     * @param username:用户名
     * @return UserDetails
     * @author : gdq
     * data : 2019/11/19 0019 16:18
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.selectUser(username);
    }

    @Override
    public List<UserPO> queryUserList() {
        return userDao.selectUserList();
    }

    @Override
    public UserPO queryUserByUsername(String username) {
        return userDao.selectUser(username);
    }

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }
}
