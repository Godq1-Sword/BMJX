package pri.gdq.service;

import pri.gdq.po.MessageTipPO;

import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 消息提醒Service
 *
 * @author : gdq
 * data : 2019-12-11 15:54
 **/
public interface MessageTipService {
    boolean addMessageTip(MessageTipPO messageTipPO);

    List<MessageTipPO> queryMessageTipList(Map<String, Object> requestMap);

    boolean deleteMessageTip(Map<String, Object> requestMap);

    int queryMessageTipAmount(String username);
}
