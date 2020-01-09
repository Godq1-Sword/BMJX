package pri.gdq.service.impl;

import pri.gdq.dao.ApplyRemakeDao;
import pri.gdq.po.ApplyRemakePO;
import pri.gdq.service.ApplyRemakeService;
import pri.gdq.service.BadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 补卡补假申请信息ServiceImpl
 *
 * @author : gdq
 * data : 2019-12-11 11:25
 **/
@Service
public class ApplyRemakeServiceImpl implements ApplyRemakeService {
    // 
    private ApplyRemakeDao applyRemakeDao;

    private BadHistoryService badHistoryService;

    @Autowired
    public void setBadHistoryService(BadHistoryService badHistoryService) {
        this.badHistoryService = badHistoryService;
    }

    @Autowired
    public ApplyRemakeServiceImpl(ApplyRemakeDao applyRemakeDao) {
        this.applyRemakeDao = applyRemakeDao;
    }

    @Override
    public boolean addApplyRemake(ApplyRemakePO applyRemakePO) {
        return applyRemakeDao.insertApplyRemake(applyRemakePO);
    }

    @Override
    public List<ApplyRemakePO> queryApplyRemakeList(Map<String, Object> requestMap) {
        return applyRemakeDao.selectApplyRemakeList(requestMap);
    }

    @Override
    public ApplyRemakePO queryApplyRemakeById(String id) {
        return applyRemakeDao.selectApplyRemakeById(id);
    }

    @Override
    public Map<String, Object> queryApplyRemakeCount(Map<String, Object> requestMap) {
        return applyRemakeDao.selectApplyRemakeCount(requestMap);
    }
}
