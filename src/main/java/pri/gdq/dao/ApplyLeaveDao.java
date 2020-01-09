package pri.gdq.dao;

import pri.gdq.po.ApplyLeavePO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 申请信息Dao
 *
 * @author : gdq
 * data : 2019-12-06 14:20
 **/
@Mapper
@Repository
public interface ApplyLeaveDao {
    /**
     * description : 插入请假记录
     *
     * @param applyLeavePO:请假PO
     * @return boolean
     * @author : gdq
     * data : 2019/12/27 0027 14:26
     */
    boolean insertApplyLeave(ApplyLeavePO applyLeavePO);

    /**
     * description : 查询请假列表
     *
     * @param requestMap:键值对
     * @return List
     * @author : gdq
     * data : 2019/12/27 0027 14:26
     */
    List<ApplyLeavePO> selectApplyLeaveList(Map<String, Object> requestMap);

    /**
     * description : 根据id查询请假记录
     *
     * @param id:主键
     * @return ApplyLeavePO
     * @author : gdq
     * data : 2019/12/27 0027 14:27
     */
    ApplyLeavePO selectApplyLeaveById(String id);

    /**
     * description : 查询用户规定时间内请假次数
     *
     * @param requestMap:键值对
     * @return Map
     * @author : gdq
     * data : 2019/12/27 0027 14:29
     */
    Map<String, Object> selectApplyLeaveCount(Map<String, Object> requestMap);

    /**
     * description : 查询7/30天内请假信息
     *
     * @param requestMap:用户名和时间段
     * @return Map
     * @author : gdq 
     * data : 2019/12/30 0030 18:11
     */ 
    List<ApplyLeavePO> selectApplyLeave730(Map<String,Object> requestMap);
}
