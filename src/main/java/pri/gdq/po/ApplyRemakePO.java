package pri.gdq.po;

import pri.gdq.util.UserUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

/**
 * program : taxoa-backend
 * description: 补卡申请PO
 *
 * @author : gdq
 * data : 2019-12-11 11:12
 **/
@Data
public class ApplyRemakePO {
    // 主键
    private String id;
    // 用户名
    private String username;
    // 姓名
    private String name;
    // 申请人用户名
    private String applyUsername;
    // 申请类型
    private int applyType;
    // 申请原因
    private String applyReason;
    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    // 缺卡缺假信息记录主键
    private String badHistoryId;

    public ApplyRemakePO() {
    }

    public ApplyRemakePO(String id, String username, String name, String applyUsername, int applyType, String applyReason, Date createTime, String badHistoryId) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.applyUsername = applyUsername;
        this.applyType = applyType;
        this.applyReason = applyReason;
        this.createTime = createTime;
        this.badHistoryId = badHistoryId;
    }


    public ApplyRemakePO(ApplyRemakePO applyRemakePO, String badHistoryId) {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.username = UserUtil.getUserName();
        this.name = UserUtil.getName();
        this.applyType = applyRemakePO.getApplyType();
        this.applyReason = applyRemakePO.getApplyReason();
        this.applyUsername = applyRemakePO.getApplyUsername();
        this.createTime = new Date();
        this.badHistoryId = badHistoryId;
    }
}
