package pri.gdq.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * program : taxoa-backend
 * description: 代码配置项
 *
 * @author : gdq
 * data : 2019-12-04 10:02
 **/
@Configuration
@ConfigurationProperties(prefix = "config.common")
@Data
public class ConfigUtil {
    // 上班时间
    public static String START_TIME;
    // 下班时间
    public static String END_TIME;
    // 行为种类 - 迟到
    public static int BEHAVIOR_LATE;
    // 行为种类 - 早退
    public static int BEHAVIOR_EARLY;
    // 行为种类 - 缺卡
    public static int BEHAVIOR_MISS;
    // 行为种类 - 旷工
    public static int BEHAVIOR_ABSENT;
    // 缺卡种类 - 不缺
    public static int MISS_NOT;
    // 缺卡种类 - 上班
    public static int MISS_CLOCK_IN;
    // 缺卡种类 - 下班
    public static int MISS_CLOCK_OUT;
    // 补卡种类 - 无需
    public static int BACK_NOT;
    // 补卡种类 - 未补卡
    public static int BACK_NOT_MISS;
    // 补卡种类 - 补卡
    public static int BACK_MISS;
    // 补卡种类 - 未补假
    public static int BACK_ABSENT;
    // 补卡种类 - 补假
    public static int BACK_NOT_ABSENT;
    // 人员最高层级
    public static int LEVEL_MAX;
    // 文件路径
    public static String FILE_PATH;
    // 未读
    public static int STATUS_UNREAD;
    // 审批通过
    public static int STATUS_PASS;
    // 审批拒绝
    public static int STATUS_NOT_PASS;
    // 申请类型 - 请假
    public static int APPLY_LEAVE;
    // 申请类型 - 外勤
    public static int APPLY_WORK;
    // 申请类型 - 出差
    public static int APPLY_BUSINESS;
    // 申请类型 - 补卡
    public static int APPLY_BACK_CARD;
    // 申请类型 - 补假
    public static int APPLY_BACK_HOLIDAY;
    // Excel路径
    public static String EXCEL_FILE_PATH;
    // 定时任务周期
    public static String CRON_ABSENT;
    // yyyy-MM-dd 日期格式
    public static String YMH_PATTERN;
    // yyyy-MM-dd HH:mm:ss 日期格式
    public static String HMS_PATTERN;
    // 签名加密算法
    public static String ALGORITHM;
    // 签名人
    public static String ISS;
    // 公钥
    public static String SECRET;
    // 有效期
    public static String EXPIRATION;

    public void setBehaviorLate(int behaviorLate) {
        BEHAVIOR_LATE = behaviorLate;
    }

    public void setBehaviorEarly(int behaviorEarly) {
        BEHAVIOR_EARLY = behaviorEarly;
    }

    public void setBehaviorMiss(int behaviorMiss) {
        BEHAVIOR_MISS = behaviorMiss;
    }

    public void setBehaviorAbsent(int behaviorAbsent) {
        BEHAVIOR_ABSENT = behaviorAbsent;
    }

    public void setMissNot(int missNot) {
        MISS_NOT = missNot;
    }

    public void setBackNot(int backNot) {
        BACK_NOT = backNot;
    }

    public void setBackMiss(int backMiss) {
        BACK_MISS = backMiss;
    }

    public void setBackAbsent(int backAbsent) {
        BACK_ABSENT = backAbsent;
    }

    public void setLevelMax(int levelMax) {
        LEVEL_MAX = levelMax;
    }

    public void setFilePath(String FILEPATH) {
        FILE_PATH = FILEPATH;
    }

    public void setStatusUnread(int statusUnread) {
        STATUS_UNREAD = statusUnread;
    }

    public void setStatusPass(int statusPass) {
        STATUS_PASS = statusPass;
    }

    public void setMissClockIn(int missClockIn) {
        MISS_CLOCK_IN = missClockIn;
    }

    public void setMissClockOut(int missClockOut) {
        MISS_CLOCK_OUT = missClockOut;
    }

    public void setBackNotMiss(int backNotMiss) {
        BACK_NOT_MISS = backNotMiss;
    }

    public void setBackNotAbsent(int backNotAbsent) {
        BACK_NOT_ABSENT = backNotAbsent;
    }

    public void setStatusNotPass(int statusNotPass) {
        STATUS_NOT_PASS = statusNotPass;
    }

    public void setApplyLeave(int applyLeave) {
        APPLY_LEAVE = applyLeave;
    }

    public void setApplyWork(int applyWork) {
        APPLY_WORK = applyWork;
    }

    public void setApplyBusiness(int applyBusiness) {
        APPLY_BUSINESS = applyBusiness;
    }

    public void setApplyBackCard(int applyBackCard) {
        APPLY_BACK_CARD = applyBackCard;
    }

    public void setApplyBackHoliday(int applyBackHoliday) {
        APPLY_BACK_HOLIDAY = applyBackHoliday;
    }

    public void setExcelFilePath(String excelFilePath) {
        EXCEL_FILE_PATH = excelFilePath;
    }

    public void setStartTime(String startTime) {
        START_TIME = startTime;
    }

    public void setEndTime(String endTime) {
        END_TIME = endTime;
    }

    public static void setCronAbsent(String cronAbsent) {
        CRON_ABSENT = cronAbsent;
    }

    public void setYmhPattern(String ymhPattern) {
        YMH_PATTERN = ymhPattern;
    }

    public void setHmsPattern(String hmsPattern) {
        HMS_PATTERN = hmsPattern;
    }

    public void setALGORITHM(String ALGORITHM) {
        ConfigUtil.ALGORITHM = ALGORITHM;
    }

    public void setISS(String ISS) {
        ConfigUtil.ISS = ISS;
    }

    public void setSECRET(String SECRET) {
        ConfigUtil.SECRET = SECRET;
    }

    public void setEXPIRATION(String EXPIRATION) {
        ConfigUtil.EXPIRATION = EXPIRATION;
    }
}
