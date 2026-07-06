package com.mark.community.service;

import com.mark.community.dto.CustomUserDetails;
import com.mark.community.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthService {
    private final HttpSessionSecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    public void changeSessionAuthorization(UserRole newRole) {
        CustomUserDetails oldPrincipal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CustomUserDetails updateUser = new CustomUserDetails(
                oldPrincipal.getId(), oldPrincipal.getEmail(), oldPrincipal.getPassword(), oldPrincipal.getFileId(),
                List.of(new SimpleGrantedAuthority(newRole.getValue()))
        );

        Authentication newAuth = UsernamePasswordAuthenticationToken.authenticated(
                updateUser, null, updateUser.getAuthorities()
        );

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(newAuth);

        securityContextRepository.saveContext(context, request, response);
    }

}
