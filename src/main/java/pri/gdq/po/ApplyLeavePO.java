package pri.gdq.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * program : taxoa-backend
 * description: 申请信息PO
 *
 * @author : gdq
 * data : 2019-12-06 14:15
 **/
@Data
public class ApplyLeavePO {
    // 主键
    private String id;
    // 用户名
    private String username;
    // 姓名
    private String name;
    // 申请类型
    private int applyType;
    // 开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    // 结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    // 申请理由
    private String applyReason;
    // 附件路径
    private String filePath;
    // 审核人用户名
    private String applyUsername;
    // 审核人姓名
    private String applyName;
    // 申请时间
    private long applyTime;
    // 假期种类
    private int holidayType;
    // 出差/外勤地点
    private String tripSite;
    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    // 申请状态
    private int applyStatus;
}

