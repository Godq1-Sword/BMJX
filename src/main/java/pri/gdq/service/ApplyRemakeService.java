package pri.gdq.service;

import pri.gdq.po.ApplyRemakePO;

import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 补卡补假申请信息Service
 *
 * @author : gdq
 * data : 2019-12-11 11:23
 **/
public interface ApplyRemakeService {
    /**
     * description : 增加补卡补假记录
     *
     * @param applyRemakePO:补卡补假PO
     * @return boolean
     * @author : gdq
     * data : 2019/12/27 0027 14:47
     */
    boolean addApplyRemake(ApplyRemakePO applyRemakePO);

    /**
     * description : 查询补卡补假记录
     *
     * @param requestMap:键值对
     * @return List
     * @author : gdq
     * data : 2019/12/27 0027 14:47
     */
    List<ApplyRemakePO> queryApplyRemakeList(Map<String, Object> requestMap);

    /**
     * description : 根据id查询补卡补假记录
     *
     * @param id:主键
     * @return ApplyRemakePO
     * @author : gdq
     * data : 2019/12/27 0027 14:47
     */
    ApplyRemakePO queryApplyRemakeById(String id);

    /**
     * description : 查询用户规定时间内补卡补假次数
     *
     * @param requestMap:键值对
     * @return Map
     * @author : gdq
     * data : 2019/12/27 0027 14:48
     */
    Map<String, Object> queryApplyRemakeCount(Map<String, Object> requestMap);
}
