package pri.gdq.service;

import pri.gdq.po.ApplyTaskPO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 申请任务Service
 *
 * @author : gdq
 * data : 2019-12-10 11:58
 **/
public interface ApplyTaskService {
    boolean addApplyTask(ApplyTaskPO applyTaskPO);

    List<ApplyTaskPO> queryApplyTaskList(Map<String, Object> requestMap);

    boolean updateStatusById(HashMap<String, Object> requestMap);

    ApplyTaskPO queryApplyTaskById(String id);
}
