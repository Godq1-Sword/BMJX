package pri.gdq.service;

import pri.gdq.po.ApplyLeavePO;

import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 申请信息Service
 *
 * @author : gdq
 * data : 2019-12-06 14:21
 **/
public interface ApplyLeaveService {
    /**
     * description : 增加请假记录
     *
     * @param applyLeavePO:请假信息
     * @return boolean
     * @author : gdq
     * data : 2019/12/27 0027 14:34
     */
    boolean addApplyLeave(ApplyLeavePO applyLeavePO);

    /**
     * description : 查询请假记录
     *
     * @param requestMap:键值对
     * @return List
     * @author : gdq
     * data : 2019/12/27 0027 14:34
     */
    List<ApplyLeavePO> queryApplyLeaveList(Map<String, Object> requestMap);

    /**
     * description : 根据id查询请假记录
     *
     * @param id:主键
     * @return ApplyLeavePO
     * @author : gdq
     * data : 2019/12/27 0027 14:34
     */
    ApplyLeavePO queryApplyLeaveById(String id);

    /**
     * description : 查询用户规定时间内请假次数
     *
     * @param requestMap:键值对
     * @return Map
     * @author : gdq
     * data : 2019/12/27 0027 14:43
     */
    Map<String, Object> queryApplyLeaveCount(Map<String,Object> requestMap);
    
    /**
     * description : 查询用户7/30天内请假信息
     *
     * @param requestMap:用户名和时间段
     * @return Map
     * @author : gdq 
     * data : 2019/12/30 0030 18:12
     */ 
    List<ApplyLeavePO> queryApplyLeave730(Map<String,Object> requestMap);
}
