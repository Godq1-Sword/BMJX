package pri.gdq.service.impl;

import pri.gdq.dao.UserDetailDao;
import pri.gdq.po.UserDetailPO;
import pri.gdq.service.UserDetailService;
import pri.gdq.util.ConfigUtil;
import pri.gdq.util.UserUtil;
import pri.gdq.vo.UserDetailTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 用户详细信息ServiceImpl
 *
 * @author : gdq
 * data : 2019-12-06 17:35
 **/
@Service
public class UserDetailServiceImpl implements UserDetailService {
    private UserDetailDao userDetailDao;

    @Autowired
    public UserDetailServiceImpl(UserDetailDao userDetailDao) {
        this.userDetailDao = userDetailDao;
    }

    @Override
    public List<UserDetailPO> queryUserDetailList(Map<String, Object> requestMap) {
        return userDetailDao.selectUserDetailList(requestMap);
    }

    @Override
    public List<UserDetailTreeVO> queryUserDetailTree() {
        String activeUsername = UserUtil.getUserName();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("level", ConfigUtil.LEVEL_MAX);
        List<UserDetailPO> userDetailList = queryUserDetailList(queryMap);
        List<UserDetailTreeVO> userDetailTreeVOList = new ArrayList<>();
        userDetailList.forEach(userDetailPO -> {
            UserDetailTreeVO userDetailTree = new UserDetailTreeVO();
            userDetailTree.setUsername(userDetailPO.getUsername());
            userDetailTree.setName(userDetailPO.getName());
            List<UserDetailTreeVO> userDetailTreeChildren = forEachUserDetailTreeChildren(userDetailTree.getUsername(), activeUsername);
            userDetailTree.setChildren(userDetailTreeChildren);
            if (activeUsername.equals(userDetailTree.getUsername())) {
                userDetailTree.setDisabled(true);
            }
            userDetailTreeVOList.add(userDetailTree);
        });
        return userDetailTreeVOList;
    }

    @Override
    public UserDetailPO queryUserDetailByUsername(String username) {
        return userDetailDao.selectUserDetailByUsername(username);
    }

    @Override
    public boolean modifyUserDetail(UserDetailPO userDetailPO) {
        return userDetailDao.updateUserDetail(userDetailPO) > 0;
    }

    @Override
    public List<String> queryUsernames() {
        return userDetailDao.selectUsernames();
    }

    private List<UserDetailTreeVO> forEachUserDetailTreeChildren(String username, String activeUsername) {
        List<UserDetailPO> userDetailVOChildrenList = userDetailDao.selectUserDetailListByPid(username);
        List<UserDetailTreeVO> userDetailTreeVOList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(userDetailVOChildrenList)) {
            userDetailVOChildrenList.forEach(userDetailPO -> {
                UserDetailTreeVO userDetailTree = new UserDetailTreeVO();
                userDetailTree.setUsername(userDetailPO.getUsername());
                userDetailTree.setName(userDetailPO.getName());
                List<UserDetailTreeVO> userDetailTreeChildren = forEachUserDetailTreeChildren(userDetailTree.getUsername(), activeUsername);
                userDetailTree.setChildren(userDetailTreeChildren);
                if (activeUsername.equals(userDetailTree.getUsername())) {
                    userDetailTree.setDisabled(true);
                }
                userDetailTreeVOList.add(userDetailTree);
            });
        }
        return userDetailTreeVOList;
    }
}
