package pri.gdq.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * program : taxoa-backend
 * description: 个人任务信息VO
 *
 * @author : gdq
 * data : 2019-12-24 14:37
 **/
@Data
public class ApplyTaskVO {
    // 用户名
    private String username;
    // 姓名
    private String name;
    // 补卡补假日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dateTime;
    // 理由
    private String applyReason;
    // 申请种类
    private int applyType;
    // 附件
    private String filePath;
    // 假期开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    // 假期结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    // 地点
    private String tripSite;
    // 缺卡种类
    private int missType;
    // 请假类型
    private int holidayType;
    // 任务主键
    private String taskId;
    // 意见
    private String suggest;
}
