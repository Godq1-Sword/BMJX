package pri.gdq.controller;

import pri.gdq.entity.ResponseBody;
import pri.gdq.po.ApplyRemakePO;
import pri.gdq.po.ApplyTaskPO;
import pri.gdq.service.ApplyRemakeService;
import pri.gdq.service.ApplyTaskService;
import pri.gdq.util.ConfigUtil;
import pri.gdq.util.ResponseBodyUtil;
import pri.gdq.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * program : taxoa-backend
 * description: 补卡补假申请信息Controller
 *
 * @author : gdq
 * data : 2019-12-11 11:27
 **/
@RestController
@RequestMapping("/applyRemake")
public class ApplyRemakeController {
    // 任务Service
    private ApplyTaskService applyTaskService;

    // 补卡补假Service
    private ApplyRemakeService applyRemakeService;

    /**
     * description : 添加一条补假补卡记录
     *
     * @param applyRemakePO:补卡补假记录
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 17:35
     */
    @PostMapping
    public ResponseBody addApplyRemake(ApplyRemakePO applyRemakePO) {
        applyRemakePO.setUsername(UserUtil.getUserName());
        applyRemakePO.setName(UserUtil.getName());
        int applyType = applyRemakePO.getApplyType();
        String[] badHistoryIds = {applyRemakePO.getBadHistoryId()};
        if (applyRemakePO.getBadHistoryId().contains(",")) {
            badHistoryIds = applyRemakePO.getBadHistoryId().split(",");
        }
        for (String badHistoryId : badHistoryIds) {
            ApplyRemakePO applyRemake = new ApplyRemakePO(applyRemakePO, badHistoryId);
            if (!applyRemakeService.addApplyRemake(applyRemake)) {
                return ResponseBodyUtil.generateErrResp(null);
            }
            ApplyTaskPO applyTaskPO = new ApplyTaskPO(applyRemake, ConfigUtil.STATUS_UNREAD);
            applyTaskService.addApplyTask(applyTaskPO);
        }
        String message = applyType == 3 ? "补卡申请成功" : "补假申请成功";
        return ResponseBodyUtil.generateSucResp(null, message);
    }

    @Autowired
    public void setApplyTaskService(ApplyTaskService applyTaskService) {
        this.applyTaskService = applyTaskService;
    }

    @Autowired
    public ApplyRemakeController(ApplyRemakeService applyRemakeService) {
        this.applyRemakeService = applyRemakeService;
    }

}
