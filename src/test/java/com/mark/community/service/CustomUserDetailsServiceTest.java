package com.mark.community.service;

import com.mark.community.entity.UploadFile;
import com.mark.community.entity.User;
import com.mark.community.enums.FileType;
import com.mark.community.enums.UserRole;
import com.mark.community.exception.CustomException;
import com.mark.community.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void 유저가_없으면_예외를_던진가다(){
        when(userRepository.findByEmailAndDeletedFalse(anyString())).thenReturn(Optional.empty());

        assertThrows(CustomException.class,
                () -> customUserDetailsService.loadUserByUsername("test@gmail.com"));
    }

    @Test
    void 유저가_있으면_CustomUserDetails를_반환한다(){
        UploadFile uploadFile = new UploadFile("photo.png", "/path", FileType.IMAGE, 1L);
        User user = new User("test@gmail.com", "zkxpqn1", "juseung", uploadFile, UserRole.ROLE_USER);
        when(userRepository.findByEmailAndDeletedFalse("test@gmail.com")).thenReturn(Optional.of(user));
        UserDetails result = customUserDetailsService.loadUserByUsername("test@gmail.com");
        assertNotNull(result);
    }


}
