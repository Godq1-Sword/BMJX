package pri.gdq.vo;

import lombok.Data;

import java.util.List;

/**
 * program : taxoa-backend
 * description: 用户信息树VO
 *
 * @author : gdq
 * data : 2019-12-06 17:38
 **/
@Data
public class UserDetailTreeVO {
    private String username;
    private String name;
    private List<UserDetailTreeVO> children;
    private boolean disabled;

    public UserDetailTreeVO(String username, String name, List<UserDetailTreeVO> children, boolean disabled) {
        this.username = username;
        this.name = name;
        this.children = children;
        this.disabled = disabled;
    }

    public UserDetailTreeVO() {

    }
}
