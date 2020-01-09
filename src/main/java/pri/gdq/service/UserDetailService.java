package pri.gdq.service;

import pri.gdq.po.UserDetailPO;
import pri.gdq.vo.UserDetailTreeVO;

import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 用户详细信息Service
 *
 * @author : gdq
 * data : 2019-12-06 17:34
 **/
public interface UserDetailService {
    /**
     * description : 查询用户信息列表
     *
     * @param requestMap:键值对
     * @return List
     * @author : gdq
     * data : 2019/12/31 0031 10:30
     */
    List<UserDetailPO> queryUserDetailList(Map<String, Object> requestMap);

    /**
     * description : 查询审核人员树
     *
     * @return List
     * @author : gdq
     * data : 2019/12/31 0031 10:30
     */
    List<UserDetailTreeVO> queryUserDetailTree();

    /**
     * description : 根据用户名查询用户信息
     *
     * @param username:用户名
     * @return UserDetailPO
     * @author : gdq
     * data : 2019/12/31 0031 10:31
     */
    UserDetailPO queryUserDetailByUsername(String username);

    /**
     * description : 更改用户信息
     *
     * @param userDetailPO:用户信息PO
     * @return boolean
     * @author : gdq
     * data : 2019/12/31 0031 14:10
     */
    boolean modifyUserDetail(UserDetailPO userDetailPO);

    /**
     * description : 查询所有用户名
     *
     * @return List
     * @author : gdq
     * data : 2019/12/31 0031 14:10
     */
    List<String> queryUsernames();
}
