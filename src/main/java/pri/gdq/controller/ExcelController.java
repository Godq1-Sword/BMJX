package pri.gdq.controller;

import pri.gdq.po.UserDetailPO;
import pri.gdq.service.ApplyLeaveService;
import pri.gdq.service.ApplyRemakeService;
import pri.gdq.service.BadHistoryService;
import pri.gdq.service.ClockHistoryService;
import pri.gdq.util.DateUtil;
import pri.gdq.util.ExcelUtil;
import pri.gdq.util.UserUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: ExcelController
 *
 * @author : gdq
 * data : 2019-12-27 10:32
 **/
@RestController
@RequestMapping("/excel")
public class ExcelController {
    // 打卡Service
    private ClockHistoryService clockHistoryService;
    // 不良记录Service
    private BadHistoryService badHistoryService;
    // 请假Service
    private ApplyLeaveService applyLeaveService;
    // 补卡Service
    private ApplyRemakeService applyRemakeService;

    /**
     * description : 导出用户考勤Excel
     *
     * @param request:http请求体
     * @param response:http响应体
     * @author : gdq
     * data : 2020/1/3 0003 17:45
     */
    @GetMapping("/user")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("startTime", request.getParameter("startTime"));
        requestMap.put("endTime", request.getParameter("endTime"));
        Map<String, Object> sheetData = new HashMap<>(requestMap);
        UserDetailPO userDetailPO = UserUtil.getUserDetail();
        Map<String, Object> user = new HashMap<>();
        requestMap.put("username", userDetailPO.getUsername());
        user.put("username", userDetailPO.getUsername());
        user.put("name", userDetailPO.getName());
        sheetData.putAll(user);
        Map<String, Object> resultMap = applyLeaveService.queryApplyLeaveCount(requestMap);
        sheetData.putAll(resultMap);
        resultMap = applyRemakeService.queryApplyRemakeCount(requestMap);
        sheetData.putAll(resultMap);
        resultMap = badHistoryService.queryBadHistoryCount(requestMap);
        sheetData.putAll(resultMap);
        resultMap = clockHistoryService.queryExportTime(requestMap);
        Object time = resultMap.get("avgWorkTime");
        long avgWorkTime = ((BigDecimal) time).longValue();
        String avgWorkTimeStr = DateUtil.getTimeText(avgWorkTime);
        resultMap.put("avgWorkTime", avgWorkTimeStr);
        sheetData.putAll(resultMap);
        String fileName = "个人考勤统计表";
        Workbook workbook = ExcelUtil.writeExcel("个人考勤统计表", sheetData);
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", StandardCharsets.UTF_8));
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
    }

    @Autowired
    public void setApplyLeaveService(ApplyLeaveService applyLeaveService) {
        this.applyLeaveService = applyLeaveService;
    }

    @Autowired
    public void setBadHistoryService(BadHistoryService badHistoryService) {
        this.badHistoryService = badHistoryService;
    }

    @Autowired
    public void setApplyRemakeService(ApplyRemakeService applyRemakeService) {
        this.applyRemakeService = applyRemakeService;
    }

    @Autowired
    public void setClockHistoryService(ClockHistoryService clockHistoryService) {
        this.clockHistoryService = clockHistoryService;
    }
}
