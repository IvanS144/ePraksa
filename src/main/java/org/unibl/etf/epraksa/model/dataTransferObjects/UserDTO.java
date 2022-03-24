package org.unibl.etf.epraksa.model.dataTransferObjects;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.unibl.etf.epraksa.model.entities.Role;

import java.util.Collection;
import java.util.List;
@Data
@NoArgsConstructor
public class UserDTO implements UserDetails {
    private Long id;
    private String loginMail;
    private String password;
    private Role role;
    private Boolean isActive;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

    public UserDTO(Long id, String loginMail, String password, Role role)
    {
        this.id=id;
        this.loginMail=loginMail;
        this.password=password;
        this.role=role;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginMail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
