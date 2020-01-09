package pri.gdq.dao;

import pri.gdq.po.ApplyTaskPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 申请任务Dao
 *
 * @author : gdq
 * data : 2019-12-10 11:49
 **/
@Mapper
@Repository
public interface ApplyTaskDao {
    /**
     * description : 插入一条任务信息
     *
     * @param applyPO:任务信息
     * @return boolean
     * @author : gdq
     * data : 2020/1/3 0003 18:12
     */
    boolean insertApplyTask(ApplyTaskPO applyPO);

    /**
     * description : 查询任务列表
     *
     * @param requestMap:键值对
     * @return List
     * @author : gdq
     * data : 2020/1/3 0003 18:13
     */
    List<ApplyTaskPO> selectApplyTaskList(Map<String, Object> requestMap);

    /**
     * description : 根据id更新任务信息
     *
     * @param requestMap:键值对
     * @return boolean
     * @author : gdq
     * data : 2020/1/3 0003 18:13
     */
    boolean updateStatusById(Map<String, Object> requestMap);

    /**
     * description : 根据id查询任务信息
     *
     * @param id:主键
     * @return ApplyTaskPO
     * @author : gdq
     * data : 2020/1/3 0003 18:13
     */
    ApplyTaskPO selectApplyTaskById(String id);
}
