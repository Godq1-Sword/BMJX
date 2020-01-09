package pri.gdq.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * program : taxoa-backend
 * description: 不良记录信息PO
 *
 * @author : gdq
 * data : 2019-12-03 16:02
 **/
@Data
public class BadHistoryPO {
    // 主键
    private String id;
    // 打卡时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date clockTime;
    // 迟到/早退时间
    private long overTime;
    // 行为种类
    private int behaviorType;
    // 用户名
    private String username;
    // 日期
    private java.sql.Date dateTime;
    // 补卡种类
    private int backType;
    // 缺卡种类
    private int missType;

    public BadHistoryPO(String id, Date clockTime, long overTime, int behaviorType, String username, java.sql.Date dateTime, int backType, int missType) {
        this.id = id;
        this.clockTime = clockTime;
        this.overTime = overTime;
        this.behaviorType = behaviorType;
        this.username = username;
        this.dateTime = dateTime;
        this.backType = backType;
        this.missType = missType;
    }

    public BadHistoryPO() {
    }
}
