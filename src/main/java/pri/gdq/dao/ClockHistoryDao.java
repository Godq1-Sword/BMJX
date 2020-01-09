package pri.gdq.dao;

import pri.gdq.po.ClockHistoryPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 打卡记录Dao
 *
 * @author : gdq
 * data : 2019-12-03 11:37
 **/
@Mapper
@Repository
public interface ClockHistoryDao {
    /**
     * description : 插入打卡记录
     *
     * @param clockHistoryPO:打卡记录PO
     * @return boolean
     * @author : gdq
     * data : 2019/12/27 0027 14:57
     */
    boolean insertClockHistory(ClockHistoryPO clockHistoryPO);

    /**
     * description : 查询打卡记录列表
     *
     * @param requestMap:键值对
     * @return List
     * @author : gdq
     * data : 2019/12/27 0027 14:57
     */
    List<ClockHistoryPO> selectClockHistoryList(Map<String, Object> requestMap);

    /**
     * description : 更新打卡记录
     *
     * @param clockHistoryPO:打卡记录PO
     * @return boolean
     * @author : gdq
     * data : 2019/12/27 0027 14:57
     */
    boolean updateClockHistory(ClockHistoryPO clockHistoryPO);

    /**
     * description : 查询用户规定时间内工作时长
     *
     * @param requestMap:键值对
     * @return Map
     * @author : gdq
     * data : 2019/12/30 0030 16:54
     */
    Map<String, Object> selectExportTime(Map<String, Object> requestMap);

    /**
     * description : 7/30天内工作平均时长和出勤百分比
     *
     * @param requestMap:用户名 - 周期
     * @return Map
     * @author : gdq
     * data : 2019/12/30 0030 16:56
     */
    Map<String, Object> selectWorkTimeAndPercent(Map<String, Object> requestMap);

    /**
     * description : 查询该日期所有打卡用户名
     *
     * @param dateTime:日期
     * @return List
     * @author : gdq
     * data : 2019/12/31 0031 14:16
     */
    List<String> selectClockUsernames(Date dateTime);
}

