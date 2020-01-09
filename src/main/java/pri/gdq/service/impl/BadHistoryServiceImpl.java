package pri.gdq.service.impl;

import pri.gdq.dao.BadHistoryDao;
import pri.gdq.po.BadHistoryPO;
import pri.gdq.service.BadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 不良记录ServiceImpl
 *
 * @author : gdq
 * data : 2019-12-03 16:11
 **/
@Service
public class BadHistoryServiceImpl implements BadHistoryService {

    private BadHistoryDao badHistoryDao;

    @Autowired
    public BadHistoryServiceImpl(BadHistoryDao badHistoryDao) {
        this.badHistoryDao = badHistoryDao;
    }

    @Override
    public boolean addBadHistory(BadHistoryPO badHistoryPO) {
        return badHistoryDao.insertBadHistory(badHistoryPO);
    }

    @Override
    public List<BadHistoryPO> queryBadHistoryList(Map<String, Object> requestMap) {
        return badHistoryDao.selectBadHistoryList(requestMap);
    }

    @Override
    public BadHistoryPO queryBadHistoryById(String id) {
        return badHistoryDao.selectBadHistoryById(id);
    }

    @Override
    public Map<String, Object> queryBadHistoryCount(Map<String, Object> requestMap) {
        return badHistoryDao.selectBadHistoryCount(requestMap);
    }
}
