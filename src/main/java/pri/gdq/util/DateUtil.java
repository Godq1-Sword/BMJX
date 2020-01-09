package pri.gdq.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * program : taxoa-backend
 * description: 系统配置项
 *
 * @author : gdq
 * data : 2019-12-03 16:15
 **/
@Component
public class DateUtil {
    // 转换类
    private static DateFormat dateFormat;
    // yyyy-MM-dd hh:mm:ss
    public static String hmsPattern;
    // yyyy-MM-dd
    public static String ymhPattern;
    // 上班时间
    private static String startTime;
    // 下班时间
    private static String endTime;

    private static Date getTime(String time, long dateTime) {
        Date date = new Date(dateTime);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time = dateFormat.format(date).substring(0, 11) + time + ":00";
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getTimeText(long time) {
        time /= 1000;
        long hours = time / (3600);
        long seconds = (time - hours * 3600) / 60;
        return hours + "时" + seconds + "分";
    }

    public static long getHours(long time) {
        return time / (60 * 60 * 1000 * 24);
    }


    public static Date dateParse(String s, String format) {
        Date date = null;
        dateFormat = new SimpleDateFormat(format);
        try {
            date = dateFormat.parse(s);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date;
    }

    public static Date dateParse(Date date, String format) {
        dateFormat = new SimpleDateFormat(format);
        try {
            String dateStr = dateFormat.format(date);
            date = dateFormat.parse(dateStr);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date;
    }

    public static String getFilePathByDate(long time) {
        dateFormat = new SimpleDateFormat("yyyy/MMdd/");
        return dateFormat.format(new Date(time));
    }

    /**
     * description : 获取前一天日期
     *
     * @return Date
     * @author : gdq
     * data : 2019/12/31 0031 14:07
     */
    public static java.sql.Date getPrevDay() {
        long time = new Date().getTime() - 24 * 3600 * 1000;
        return new java.sql.Date(time);
    }


    @Value("${config.common.ymh-pattern}")
    public void setYmhPattern(String ymhPattern) {
        DateUtil.ymhPattern = ymhPattern;
    }

    @Value("${config.common.hms-pattern}")
    public void setHmsPattern(String hmsPattern) {
        DateUtil.hmsPattern = hmsPattern;
    }

    @Value("${config.common.start-time}")
    public void setStartTime(String startTime) {
        DateUtil.startTime = startTime;
    }

    @Value("${config.common.end-time}")
    public void setEndTime(String endTime) {
        DateUtil.endTime = endTime;
    }

    public static Date getStartTime(long dateTime) {
        return getTime(startTime, dateTime);
    }

    public static Date getEndTime(long dateTime) {
        return getTime(endTime, dateTime);
    }
}
