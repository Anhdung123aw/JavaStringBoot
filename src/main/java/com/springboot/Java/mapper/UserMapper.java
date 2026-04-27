package com.springboot.Java.mapper;


import com.springboot.Java.dto.request.UserCreationRequest;
import com.springboot.Java.dto.request.UserUpdateRequest;
import com.springboot.Java.dto.response.UserResponse;
import com.springboot.Java.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
    void  updateUser(@MappingTarget User user, UserUpdateRequest request);
}
