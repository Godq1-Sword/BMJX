package pri.gdq.dao;

import pri.gdq.po.MissHistoryPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * program : taxoa-backend
 * description: 缺卡信息Dao
 *
 * @author : gdq
 * data : 2019-12-05 10:17
 **/
@Mapper
@Repository
public interface MissHistoryDao {
    /**
     * description : 插入缺卡记录
     *
     * @param missHistoryPO:缺卡PO
     * @return boolean
     * @author : gdq
     * data : 2020/1/3 0003 18:15
     */
    boolean insertMissHistory(MissHistoryPO missHistoryPO);

    /**
     * description : 查询缺卡记录
     *
     * @param requestMap:键值对
     * @return List
     * @author : gdq
     * data : 2020/1/3 0003 18:15
     */
    List<MissHistoryPO> selectMissHistoryList(Map<String, Object> requestMap);
}
