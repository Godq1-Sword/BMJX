package pri.gdq.service.impl;

import pri.gdq.dao.MessageTipDao;
import pri.gdq.po.MessageTipPO;
import pri.gdq.service.MessageTipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 消息提醒ServiceImpl
 *
 * @author : gdq
 * data : 2019-12-11 15:55
 **/
@Service
@Transactional
public class MessageTipServiceImpl implements MessageTipService {
    // 消息Dao
    private MessageTipDao messageTipDao;

    @Autowired
    public MessageTipServiceImpl(MessageTipDao messageTipDao) {
        this.messageTipDao = messageTipDao;
    }

    @Override
    public boolean addMessageTip(MessageTipPO messageTipPO) {
        return messageTipDao.insertMessageTip(messageTipPO);
    }

    @Override
    public List<MessageTipPO> queryMessageTipList(Map<String, Object> requestMap) {
        return messageTipDao.selectMessageTipList(requestMap);
    }

    @Override
    public boolean deleteMessageTip(Map<String, Object> requestMap) {
        return messageTipDao.deleteMessageTip(requestMap);
    }

    @Override
    public int queryMessageTipAmount(String username) {
        return messageTipDao.selectMessageTipAmount(username);
    }
}
