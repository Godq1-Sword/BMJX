package pri.gdq.po;

import pri.gdq.util.UserUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

/**
 * program : taxoa-backend
 * description: 申请任务PO
 *
 * @author : gdq
 * data : 2019-12-10 11:01
 **/
@Data
public class ApplyTaskPO {
    // 主键
    private String id;
    // 审批人用户名
    private String applyUsername;
    // 审批人姓名
    private String applyName;
    // 申请信息主键
    private String applyId;
    // 申请人用户名
    private String username;
    // 申请人姓名
    private String name;
    // 消息类型
    private int applyType;
    // 状态
    private int status;
    // 申请日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date applyCreateTime;
    // 更新日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    // 建议
    private String suggest;

    public ApplyTaskPO(ApplyLeavePO applyLeavePO, int status) {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.applyId = applyLeavePO.getId();
        this.applyUsername = applyLeavePO.getApplyUsername();
        this.applyName = applyLeavePO.getApplyName();
        this.applyCreateTime = applyLeavePO.getCreateTime();
        this.status = status;
        this.applyType = applyLeavePO.getApplyType();
        this.username = UserUtil.getUserName();
        this.name = UserUtil.getName();
        this.suggest = null;
    }

    public ApplyTaskPO(ApplyRemakePO applyRemakePO, int status) {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.applyId = applyRemakePO.getId();
        this.applyUsername = applyRemakePO.getApplyUsername();
        this.applyName = applyRemakePO.getName();
        this.applyCreateTime = applyRemakePO.getCreateTime();
        this.status = status;
        this.applyType = applyRemakePO.getApplyType();
        this.username = UserUtil.getUserName();
        this.name = UserUtil.getName();
        this.suggest = null;
    }

    public ApplyTaskPO() {
    }
}
