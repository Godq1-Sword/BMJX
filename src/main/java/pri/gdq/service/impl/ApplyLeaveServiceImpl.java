package pri.gdq.service.impl;

import pri.gdq.dao.ApplyLeaveDao;
import pri.gdq.po.ApplyLeavePO;
import pri.gdq.service.ApplyLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 申请信息ServiceImpl
 *
 * @author : gdq
 * data : 2019-12-06 14:21
 **/
@Service
public class ApplyLeaveServiceImpl implements ApplyLeaveService {
    // 请假Dao
    private ApplyLeaveDao applyLeaveDao;

    @Autowired
    public ApplyLeaveServiceImpl(ApplyLeaveDao applyLeaveDao) {
        this.applyLeaveDao = applyLeaveDao;
    }

    @Override
    public boolean addApplyLeave(ApplyLeavePO applyLeavePO) {
        return applyLeaveDao.insertApplyLeave(applyLeavePO);
    }

    @Override
    public List<ApplyLeavePO> queryApplyLeaveList(Map<String, Object> requestMap) {
        return applyLeaveDao.selectApplyLeaveList(requestMap);
    }

    @Override
    public ApplyLeavePO queryApplyLeaveById(String id) {
        return applyLeaveDao.selectApplyLeaveById(id);
    }

    @Override
    public Map<String, Object> queryApplyLeaveCount(Map<String, Object> requestMap) {
        return applyLeaveDao.selectApplyLeaveCount(requestMap);
    }

    @Override
    public List<ApplyLeavePO> queryApplyLeave730(Map<String, Object> requestMap) {
        return applyLeaveDao.selectApplyLeave730(requestMap);
    }

}
