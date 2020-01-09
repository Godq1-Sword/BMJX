package pri.gdq.controller;

import pri.gdq.entity.ResponseBody;
import pri.gdq.po.MessageTipPO;
import pri.gdq.service.MessageTipService;
import pri.gdq.util.ResponseBodyUtil;
import pri.gdq.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * program : taxoa-backend
 * description: 消息提醒Controller
 *
 * @author : gdq
 * data : 2019-12-11 15:56
 **/
@RestController
@RequestMapping("/messageTip")
public class MessageTipController {

    // 消息Service
    private MessageTipService messageTipService;

    /**
     * description : 插入一条消息
     *
     * @param messageTipPO:消息PO
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 17:52
     */
    @PostMapping
    public ResponseBody addMessageTip(MessageTipPO messageTipPO) {
        return ResponseBodyUtil.generateSucResp(messageTipService.addMessageTip(messageTipPO), "插入成功");
    }

    /**
     * description :加载用户消息列表
     *
     * @param requestMap:键值对
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 17:52
     */
    @GetMapping("/user")
    public ResponseBody loadTableData(Map<String, Object> requestMap) {
        return ResponseBodyUtil.generateSucResp(messageTipService.queryMessageTipList(requestMap), "加载数据成功");
    }

    /**
     * description : 根据id删除消息
     *
     * @param requestMap:键值对
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 17:53
     */
    @DeleteMapping("/id")
    public ResponseBody deleteTableRow(@RequestBody Map<String, Object> requestMap) {
        return ResponseBodyUtil.generateSucResp(messageTipService.deleteMessageTip(requestMap), "删除成功");
    }

    /**
     * description : 查询用户消息总数
     *
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 17:53
     */
    @GetMapping("/amount")
    public ResponseBody loadSideBarAmount() {
        return ResponseBodyUtil.generateSucResp(messageTipService.queryMessageTipAmount(UserUtil.getUserName()), "加载数据成功");
    }

    @Autowired
    public MessageTipController(MessageTipService messageTipService) {
        this.messageTipService = messageTipService;
    }

}
