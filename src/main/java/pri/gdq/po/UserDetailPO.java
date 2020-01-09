package pri.gdq.po;

import lombok.Data;

/**
 * program : taxoa-backend
 * description: 用户详细信息PO
 *
 * @author : gdq
 * data : 2019-12-06 17:27
 **/
@Data
public class UserDetailPO {
    // 用户名
    private String username;
    // 姓名
    private String name;
    // 上级节点用户名
    private String pid;
    // 层级
    private int level;

    public UserDetailPO() {
    }
}
