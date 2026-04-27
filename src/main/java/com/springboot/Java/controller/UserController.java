package com.springboot.Java.controller;

import com.springboot.Java.dto.request.ApiResponse;
import com.springboot.Java.dto.request.UserCreationRequest;
import com.springboot.Java.dto.request.UserUpdateRequest;
import com.springboot.Java.dto.response.UserResponse;
import com.springboot.Java.service.UserService;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("User name: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<UserResponse>>builder().result(userService.getUsers()).build();
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable("userId") String userId){
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return "User has been deleted";
    }
}