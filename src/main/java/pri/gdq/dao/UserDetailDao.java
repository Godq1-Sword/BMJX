package pri.gdq.dao;

import pri.gdq.po.UserDetailPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 用户详细信息Dao
 *
 * @author : gdq
 * data : 2019-12-06 17:30
 **/
@Mapper
@Repository
public interface UserDetailDao {
    /**
     * description : 查询用户信息列表
     *
     * @param requestMap:键值对
     * @return List
     * @author : gdq
     * data : 2019/12/31 0031 10:32
     */
    List<UserDetailPO> selectUserDetailList(Map<String, Object> requestMap);

    /**
     * description : 根据父级用户名查询树形图
     *
     * @param username:父级用户名
     * @return List
     * @author : gdq
     * data : 2019/12/31 0031 10:32
     */
    List<UserDetailPO> selectUserDetailListByPid(String username);

    /**
     * description : 根据用户名查询用户信息
     *
     * @param username:用户名
     * @return UserDetailPO
     * @author : gdq
     * data : 2019/12/31 0031 10:33
     */
    UserDetailPO selectUserDetailByUsername(String username);

    /**
     * description : 更新用户信息
     *
     * @param userDetailPO:用户信息
     * @return int
     * @author : gdq
     * data : 2019/12/31 0031 10:34
     */
    int updateUserDetail(UserDetailPO userDetailPO);

    /**
     * description : 查询所有用户名
     *
     * @return List
     * @author : gdq
     * data : 2019/12/31 0031 14:10
     */
    List<String> selectUsernames();
}
