package pri.gdq.dao;

import pri.gdq.po.MessageTipPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 消息提醒Dao
 *
 * @author : gdq
 * data : 2019-12-11 15:48
 **/
@Mapper
@Repository
public interface MessageTipDao {
    /**
     * description : 插入一条消息
     *
     * @param messageTipPO:消息PO
     * @return boolean
     * @author : gdq
     * data : 2020/1/3 0003 18:14
     */
    boolean insertMessageTip(MessageTipPO messageTipPO);

    /**
     * description : 查询消息列表
     *
     * @param requestMap:键值对
     * @return List
     * @author : gdq
     * data : 2020/1/3 0003 18:14
     */
    List<MessageTipPO> selectMessageTipList(Map<String, Object> requestMap);

    /**
     * description : 删除消息
     *
     * @param requestMap:键值对
     * @return boolean
     * @author : gdq
     * data : 2020/1/3 0003 18:14
     */
    boolean deleteMessageTip(Map<String, Object> requestMap);

    /**
     * description : 查询用户消息数量
     *
     * @param username:用户名
     * @return int
     * @author : gdq
     * data : 2020/1/3 0003 18:15
     */
    int selectMessageTipAmount(String username);
}
