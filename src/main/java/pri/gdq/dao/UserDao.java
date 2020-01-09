package pri.gdq.dao;

import pri.gdq.po.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * program : taxoa-backend
 * description: 用户信息Dao
 *
 * @author : gdq
 * data : 2019-11-19 16:24
 **/
@Repository
@Mapper
public interface UserDao {
    /**
     * description : 根据用户名查询用户信息
     *
     * @param username:用户名
     * @return UserPO
     * @author : gdq
     * data : 2020/1/3 0003 18:16
     */
    UserPO selectUser(String username);

    /**
     * description : 查询用户列表
     *
     * @return List
     * @author : gdq
     * data : 2020/1/3 0003 18:16
     */
    List<UserPO> selectUserList();
}
