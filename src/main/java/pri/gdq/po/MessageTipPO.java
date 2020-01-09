package pri.gdq.po;

import pri.gdq.util.ConfigUtil;
import pri.gdq.util.DateUtil;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * program : taxoa-backend
 * description: 消息提醒PO
 *
 * @author : gdq
 * data : 2019-12-11 15:46
 **/
@Data
public class MessageTipPO {
    // 主键
    private String id;
    // 消息行为类型
    private int behaviorType;
    // 用户名
    private String username;
    // 创建时间
    private Date createTime;
    // 消息时间
    private java.sql.Date dateTime;
    // 内容
    private String content;
    // 缺卡种类
    private int missType;
    // 行为主键
    private String badHistoryId;


    public MessageTipPO() {
    }

    public MessageTipPO(String id, int behaviorType, String username, Date createTime, java.sql.Date dateTime, String content, int missType, String badHistoryId) {
        this.id = id;
        this.behaviorType = behaviorType;
        this.username = username;
        this.createTime = createTime;
        this.dateTime = dateTime;
        this.content = content;
        this.missType = missType;
        this.badHistoryId = badHistoryId;
    }

    public MessageTipPO(BadHistoryPO badHistoryPO) {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.behaviorType = badHistoryPO.getBehaviorType();
        this.username = badHistoryPO.getUsername();
        this.createTime = new Date();
        this.dateTime = badHistoryPO.getDateTime();
        this.missType = badHistoryPO.getMissType();
        this.badHistoryId = badHistoryPO.getId();
        this.content = generateContent(badHistoryPO.getOverTime());
    }

    private String generateContent(long overTime) {
        StringBuilder stringBuilder = new StringBuilder();
        if (behaviorType == ConfigUtil.BEHAVIOR_LATE) {
            stringBuilder.append("迟到提醒 - 迟到").append(DateUtil.getTimeText(overTime));
        } else if (behaviorType == ConfigUtil.BEHAVIOR_EARLY) {
            stringBuilder.append("早退提醒 - 早退").append(DateUtil.getTimeText(overTime));
        } else if (behaviorType == ConfigUtil.BEHAVIOR_MISS) {
            stringBuilder.append("补卡提醒 - ");
            if (missType == 0) {
                stringBuilder.append("上班卡未打");
            } else {
                stringBuilder.append("下班卡未打");
            }
        } else if (behaviorType == ConfigUtil.BEHAVIOR_ABSENT) {
            stringBuilder.append("旷工警告 - 未来上班");
        }
        return stringBuilder.toString();
    }
}