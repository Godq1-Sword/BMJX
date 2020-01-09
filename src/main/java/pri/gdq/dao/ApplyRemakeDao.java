package pri.gdq.dao;

import pri.gdq.po.ApplyRemakePO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 补卡补假申请信息Dao
 *
 * @author : gdq
 * data : 2019-12-11 11:16
 **/
@Mapper
@Repository
public interface ApplyRemakeDao {
    /**
     * description : 插入补卡补假记录
     *
     * @param applyRemakePO:补卡PO
     * @return boolean
     * @author : gdq
     * data : 2019/12/27 0027 14:36
     */
    boolean insertApplyRemake(ApplyRemakePO applyRemakePO);

    /**
     * description : 查询补卡补假记录
     *
     * @param requestMap:键值对
     * @return List
     * @author : gdq
     * data : 2019/12/27 0027 14:36
     */
    List<ApplyRemakePO> selectApplyRemakeList(Map<String, Object> requestMap);

    /**
     * description : 根据id查询补卡补假记录
     *
     * @param id:主键
     * @return ApplyRemakePO
     * @author : gdq
     * data : 2019/12/27 0027 14:37
     */
    ApplyRemakePO selectApplyRemakeById(String id);

    /**
     * description : 查询用户规定时间内补卡补假次数
     *
     * @param requestMap:键值对
     * @return Map
     * @author : gdq
     * data : 2019/12/27 0027 14:44
     */
    Map<String, Object> selectApplyRemakeCount(Map<String, Object> requestMap);
}
