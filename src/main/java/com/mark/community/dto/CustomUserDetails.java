package com.mark.community.dto;

import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private Long fileId;

    public CustomUserDetails(Long id, String email, String password, Long fileId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fileId = fileId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
