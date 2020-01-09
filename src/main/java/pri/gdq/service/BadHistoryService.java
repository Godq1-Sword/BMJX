package pri.gdq.service;

import pri.gdq.po.BadHistoryPO;

import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 不良记录Service
 *
 * @author : gdq
 * data : 2019-12-03 16:10
 **/
public interface BadHistoryService {
    /**
     * description : 增加不良记录
     *
     * @param badHistoryPO:不良信息PO
     * @return boolean
     * @author : gdq
     * data : 2019/12/27 0027 16:12
     */
    boolean addBadHistory(BadHistoryPO badHistoryPO);

    /**
     * description : 查询不良记录
     *
     * @param requestMap:键值对
     * @return List
     * @author : gdq
     * data : 2019/12/27 0027 16:13
     */
    List<BadHistoryPO> queryBadHistoryList(Map<String, Object> requestMap);

    /**
     * description : 根据id查询不良记录
     *
     * @param id:主键
     * @return BadHistoryPO
     * @author : gdq
     * data : 2019/12/27 0027 16:13
     */
    BadHistoryPO queryBadHistoryById(String id);

    /**
     * description :查询不良记录行为次数
     *
     * @param requestMap:键值对
     * @return Map
     * @author : gdq
     * data : 2019/12/27 0027 16:13
     */
    Map<String,Object> queryBadHistoryCount(Map<String,Object> requestMap);
}
