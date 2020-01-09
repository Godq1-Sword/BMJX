package pri.gdq.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Arrays;
import java.util.Date;

/**
 * program : taxoa-backend
 * description: 日历VO
 *
 * @author : gdq
 * data : 2019-12-30 14:47
 **/
@Data
public class CalendarVO {
    // 上班打卡时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    // 下班打卡时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    // 行为类型
    private int[] behaviorTypes;
    // 请假类型
    private int applyType;
    // 工时说明
    private String workTimeTxt;
    // 缺卡类型
    private int[] missTypes;
    // 补卡类型
    private int[] backTypes;
}



