package com.mark.community.service;

import com.mark.community.dto.LoginRequest;
import com.mark.community.entity.User;
import com.mark.community.exception.CustomException;
import com.mark.community.messages.ApiResponseErrorMessage;
import com.mark.community.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User loginUser(LoginRequest request) {
        return userRepository.findByEmailAndPasswordAndDeletedFalse(request.getEmail(), request.getPassword()).orElseThrow(
                () -> new CustomException(ApiResponseErrorMessage.FAIL_LOGIN));
    }
}
