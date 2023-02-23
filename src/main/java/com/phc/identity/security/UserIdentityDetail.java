package com.phc.identity.security;

import com.phc.identity.entity.UserIdentity;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserIdentityDetail implements UserDetails {

    private UserIdentity userIdentity;

    private String userName;
    private String password;
    private List<GrantedAuthority> authorities;
    public UserIdentityDetail(UserIdentity userIdentity) {
        this.userName = userIdentity.getUserName();
        this.password = userIdentity.getPassword();
        this.authorities = Arrays.stream(userIdentity.getAuthorities().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

//        final List<String> auths = List.of(userIdentity.getAuthorities().split(","));
//        return auths.stream().map(a->new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return a;
//            }
//        }).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
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
}
