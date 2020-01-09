package pri.gdq.po;

import lombok.Data;

import java.sql.Date;

/**
 * program : taxoa-backend
 * description: 缺卡信息PO
 *
 * @author : gdq
 * data : 2019-12-05 10:14
 **/
@Data
public class MissHistoryPO {
    // 主键
    private String id;
    // 用户名
    private String username;
    // 日期
    private Date dateTime;
    // 缺卡类型
    private int missType;
    // 是否补卡
    private int backFlag;
}
