package com.springboot.Java.service;

import com.springboot.Java.dto.request.UserCreationRequest;
import com.springboot.Java.dto.request.UserUpdateRequest;
import com.springboot.Java.dto.response.UserResponse;
import com.springboot.Java.entity.User;
import com.springboot.Java.enums.Role;
import com.springboot.Java.exception.AppException;
import com.springboot.Java.exception.ErrorCode;
import com.springboot.Java.mapper.UserMapper;
import com.springboot.Java.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@EnableWebSecurity
@EnableMethodSecurity
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        if (request.getRoles() == null || request.getRoles().isEmpty()) {
            user.setRoles(Set.of(Role.USER.name()));
        } else {
            user.setRoles(request.getRoles());
        }
        return userMapper.toUserResponse(userRepository.save(user));

    }

    public List<UserResponse> getUsers() {
        log.info("In method get Users");
        // Kiểm tra quyền của người dùng trước khi lấy danh sách người dùng
        var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean isAdmin = authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new AppException(ErrorCode.UNAUTHORIZED);  // Nếu không phải ADMIN, throw lỗi
        }
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}