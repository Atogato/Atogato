package portfolio.backend.api.auth.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import portfolio.backend.api.auth.entity.SiteUser;

import lombok.Data;
public class PrincipalDetails implements UserDetails, OAuth2User{
    private static final long serialVersionUID = 1L;
    private SiteUser user;
    private Map<String, Object> attributes;

    // 일반 로그인
    public PrincipalDetails(SiteUser user) {
        this.user = user;
    }

    // OAuth 로그인
    public PrincipalDetails(SiteUser user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    public SiteUser getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<GrantedAuthority>();
//        collect.add(()->{ return user.getUserType();});
        return collect;
    }

    // 리소스 서버로 부터 받는 회원정보
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // User의 PrimaryKey
    @Override
    public String getName() {
        return user.getId()+"";
    }

}
