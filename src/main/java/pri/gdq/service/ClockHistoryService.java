package pri.gdq.service;

import pri.gdq.po.ClockHistoryPO;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 打卡记录Service
 *
 * @author : gdq
 * data : 2019-12-03 12:12
 **/
public interface ClockHistoryService {
    /**
     * description : 插入打卡记录
     *
     * @param requestMap:用户名和时间
     * @return boolean
     * @author : gdq
     * data : 2019/12/3 0003 13:58
     */
    boolean addClockHistory(Map<String, Object> requestMap);

    /**
     * description : 查询打卡记录
     *
     * @param requestMap:属性键值对
     * @return 打卡信息List
     * @author : gdq
     * data : 2019/12/13 0013 10:43
     */
    List<ClockHistoryPO> queryClockHistoryList(Map<String, Object> requestMap);

    /**
     * description : 更新打卡记录
     *
     * @param clockHistoryPO:打卡信息
     * @return boolean
     * @author : gdq
     * data : 2019/12/13 0013 11:39
     */
    boolean modifyClockHistory(ClockHistoryPO clockHistoryPO);

    /**
     * description : 查询用户规定时间内平均工时,上班天数
     *
     * @param requestMap:键值对
     * @return double
     * @author : gdq
     * data : 2019/12/27 0027 14:55
     */
    Map<String, Object> queryExportTime(Map<String, Object> requestMap);

    /**
     * description : 查询7/30天内工作平均时长和百分比
     *
     * @param requestMap:键值对
     * @return Map
     * @author : gdq
     * data : 2019/12/30 0030 17:01
     */
    Map<String, Object> queryWorkTimeAndPercent(Map<String, Object> requestMap);

    /**
     * description : 查询该日期所有打卡用户名
     *
     * @param dateTime:日期
     * @return List
     * @author : gdq
     * data : 2019/12/31 0031 14:17
     */
    List<String> queryClockUsernames(Date dateTime);
}
