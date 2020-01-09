package pri.gdq.controller;

import pri.gdq.entity.ResponseBody;
import pri.gdq.po.ApplyLeavePO;
import pri.gdq.po.ApplyTaskPO;
import pri.gdq.service.ApplyLeaveService;
import pri.gdq.service.ApplyTaskService;
import pri.gdq.util.ConfigUtil;
import pri.gdq.util.DateUtil;
import pri.gdq.util.ResponseBodyUtil;
import pri.gdq.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * program : taxoa-backend
 * description: 申请信息Controller
 *
 * @author : gdq
 * data : 2019-12-06 14:37
 **/
@RestController
@RequestMapping("/applyLeave")
public class ApplyLeaveController {

    // 任务Service
    private ApplyTaskService applyTaskService;

    // 请假Service
    private ApplyLeaveService applyLeaveService;

    /**
     * description : 插入一条请假信息
     *
     * @param applyLeavePO:请假信息
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 17:33
     */
    @PostMapping
    public ResponseBody addApply(ApplyLeavePO applyLeavePO) {
        applyLeavePO.setUsername(UserUtil.getUserName());
        applyLeavePO.setName(UserUtil.getName());
        // 申请信息插入
        applyLeavePO.setId(UUID.randomUUID().toString().replace("-", ""));
        applyLeavePO.setCreateTime(new Date());
        boolean bool = applyLeaveService.addApplyLeave(applyLeavePO);
        // 申请任务信息插入
        ApplyTaskPO applyTaskPO = new ApplyTaskPO(applyLeavePO, ConfigUtil.STATUS_UNREAD);
        return bool & applyTaskService.addApplyTask(applyTaskPO) ? ResponseBodyUtil.generateSucResp(null, "申请任务成功") : ResponseBodyUtil.generateErrResp(null);
    }

    /**
     * description : 根据id查询请假信息
     *
     * @param requestMap:键值对
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 17:33
     */
    @GetMapping("/id")
    public ResponseBody getApply(@RequestParam Map<String, Object> requestMap) {
        List<ApplyLeavePO> applyLeavePOList = applyLeaveService.queryApplyLeaveList(requestMap);
        if (!ObjectUtils.isEmpty(applyLeavePOList)) {
            return ResponseBodyUtil.generateSucResp(applyLeaveService.queryApplyLeaveList(requestMap).get(0), "加载成功");
        }
        return ResponseBodyUtil.generateErrResp("查无信息");
    }

    /**
     * description : 查询用户7/30内请假信息
     *
     * @param requestMap:用户名和时间段
     * @return ResponseBody
     * @author : gdq
     * data : 2019/12/30 0030 18:14
     */
    @GetMapping("/user")
    public ResponseBody loadApply730(@RequestParam Map<String, Object> requestMap) {
        List<ApplyLeavePO> applyLeavePOList = applyLeaveService.queryApplyLeave730(requestMap);
        Date now = DateUtil.dateParse(new Date(), "yyyy-MM-dd");
        if (ObjectUtils.isEmpty(applyLeavePOList)) {
            applyLeavePOList.forEach(applyLeavePO -> {
                if (applyLeavePO.getEndTime().after(now)) {
                    applyLeavePO.setEndTime(now);
                }
            });
        }
        return ResponseBodyUtil.generateSucResp(applyLeavePOList, "加载规定时间内请假信息成功");
    }

    @Autowired
    public void setApplyTaskService(ApplyTaskService applyTaskService) {
        this.applyTaskService = applyTaskService;
    }

    @Autowired
    public ApplyLeaveController(ApplyLeaveService applyLeaveService) {
        this.applyLeaveService = applyLeaveService;
    }
}
