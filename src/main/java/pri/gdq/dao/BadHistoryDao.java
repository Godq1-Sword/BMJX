package pri.gdq.dao;

import pri.gdq.po.BadHistoryPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 不良记录信息Dao
 *
 * @author : gdq
 * data : 2019-12-03 16:04
 **/
@Mapper
@Repository
public interface BadHistoryDao {
    /**
     * description : 插入不良记录
     *
     * @param badHistoryPO:不良记录PO
     * @return boolean
     * @author : gdq
     * data : 2019/12/27 0027 16:14
     */
    boolean insertBadHistory(BadHistoryPO badHistoryPO);

    /**
     * description : 查询不良记录
     *
     * @param requestMap:键值对
     * @return List
     * @author : gdq
     * data : 2019/12/27 0027 16:14
     */
    List<BadHistoryPO> selectBadHistoryList(Map<String, Object> requestMap);

    /**
     * description : 根据id查询不良记录
     *
     * @param id:主键
     * @return BadHistory
     * @author : gdq
     * data : 2019/12/27 0027 16:15
     */
    BadHistoryPO selectBadHistoryById(String id);

    /**
     * description : 查询用户规定时间内不良记录
     *
     * @param requestMap:键值对
     * @return Map
     * @author : gdq
     * data : 2019/12/27 0027 16:15
     */
    Map<String, Object> selectBadHistoryCount(Map<String, Object> requestMap);
}
