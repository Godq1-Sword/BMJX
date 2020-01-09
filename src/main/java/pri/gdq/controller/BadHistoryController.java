package pri.gdq.controller;

import pri.gdq.entity.ResponseBody;
import pri.gdq.service.BadHistoryService;
import pri.gdq.util.ResponseBodyUtil;
import pri.gdq.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * program : taxoa-backend
 * description: 不良行为记录Controller
 *
 * @author : gdq
 * data : 2019-12-04 10:18
 **/
@RestController
@RequestMapping("/badHistory")
public class BadHistoryController {

    // 不良记录Service
    private BadHistoryService badHistoryService;

    /**
     * description : 加载用户不良记录
     *
     * @param requestMap:键值对
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 17:40
     */
    @GetMapping
    public ResponseBody loadBadHistoryListByUserName(@RequestParam Map<String, Object> requestMap) {
        if (ObjectUtils.isEmpty(requestMap.get("username"))) {
            requestMap.put("username", UserUtil.getUserName());
        }
        return ResponseBodyUtil.generateSucResp(badHistoryService.queryBadHistoryList(requestMap), "加载成功");
    }

    @Autowired
    public BadHistoryController(BadHistoryService badHistoryService) {
        this.badHistoryService = badHistoryService;
    }

}
