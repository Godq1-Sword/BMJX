package pri.gdq.service.impl;

import pri.gdq.dao.ApplyTaskDao;
import pri.gdq.po.ApplyTaskPO;
import pri.gdq.service.ApplyTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 申请任务ServiceImpl
 *
 * @author : gdq
 * data : 2019-12-10 11:58
 **/
@Service
public class ApplyTaskServiceImpl implements ApplyTaskService {
    private ApplyTaskDao applyTaskDao;

    @Autowired
    public ApplyTaskServiceImpl(ApplyTaskDao applyTaskDao) {
        this.applyTaskDao = applyTaskDao;
    }

    @Override
    public boolean addApplyTask(ApplyTaskPO applyTaskPO) {
        return applyTaskDao.insertApplyTask(applyTaskPO);
    }

    @Override
    public List<ApplyTaskPO> queryApplyTaskList(Map<String, Object> requestMap) {
        return applyTaskDao.selectApplyTaskList(requestMap);
    }

    @Override
    public boolean updateStatusById(HashMap<String, Object> requestMap) {
        return applyTaskDao.updateStatusById(requestMap);
    }

    @Override
    public ApplyTaskPO queryApplyTaskById(String id) {
        return applyTaskDao.selectApplyTaskById(id);
    }
}
