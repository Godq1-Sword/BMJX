package pri.gdq.po;

import lombok.Data;

import java.util.Date;

/**
 * program : taxoa-backend
 * description: 打卡记录PO
 *
 * @author : gdq
 * data : 2019-12-03 11:44
 **/
@Data
public class ClockHistoryPO {
    // 主键id
    private String id;
    // 用户名
    private String username;
    // 上班打卡时间
    private Date startTime;
    // 下班打卡时间
    private Date endTime;
    // 日期
    private java.sql.Date dateTime;
    // 工时
    private long workTime;

    public ClockHistoryPO() {
    }

    public ClockHistoryPO(String id, String username, Date startTime, Date endTime, java.sql.Date dateTime, long workTime) {
        this.id = id;
        this.username = username;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dateTime = dateTime;
        this.workTime = workTime;
    }
}
