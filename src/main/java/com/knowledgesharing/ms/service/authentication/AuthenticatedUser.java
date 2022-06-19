package com.knowledgesharing.ms.service.authentication;

import com.knowledgesharing.ms.entities.UserAccessDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class AuthenticatedUser implements UserDetails {

    private final Collection<GrantedAuthority> authorities;
    private UserAccessDetails userAccessDetails;

    public AuthenticatedUser(UserAccessDetails userAccessDetails) {
        if (Objects.isNull(userAccessDetails)) {
            throw new IllegalArgumentException("Please provide a valid user");
        } else {
            this.userAccessDetails = userAccessDetails;
            this.authorities = this.initAuthorities(userAccessDetails);
        }
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String getPassword() {
        return "NA";
    }

    public String getUsername() {
        return this.userAccessDetails.getName();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    private Collection<GrantedAuthority> initAuthorities(UserAccessDetails user) {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + user.getRole());
        return List.of(simpleGrantedAuthority);
    }
}
