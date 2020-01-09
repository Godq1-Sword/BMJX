package pri.gdq.po;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * program : taxoa-backend
 * description: 用户信息PO
 *
 * @author : gdq
 * data : 2019-11-14 15:50
 **/
@Data
public class UserPO implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 角色
    private String role;
    // 账号过期时间
    private Date accountExpires;
    // 密码过期时间
    private Date credentialsExpires;
    // 锁标志
    private boolean lockFlag;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (StringUtils.isEmpty(role)) {
            return null;
        }
        String[] roles = role.split("\\.");
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String s : roles) {
            authorities.add(new SimpleGrantedAuthority(s));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return ObjectUtils.isEmpty(accountExpires) || new Date().compareTo(accountExpires) < 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !lockFlag;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return ObjectUtils.isEmpty(accountExpires) || new Date().compareTo(credentialsExpires) < 0;
    }

    @Override
    public boolean isEnabled() {
        return isAccountNonExpired() && isAccountNonLocked() && isCredentialsNonExpired();
    }
}
