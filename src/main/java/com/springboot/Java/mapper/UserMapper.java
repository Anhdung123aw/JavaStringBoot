package com.springboot.Java.mapper;

import com.springboot.Java.dto.request.UserCreationRequest;
import com.springboot.Java.dto.request.UserUpdateRequest;
import com.springboot.Java.dto.response.UserResponse;
import com.springboot.Java.entity.Role;
import com.springboot.Java.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Thêm @Mapping ignore id vì id hiện tại là AUTO_INCREMENT trong DB
    @Mapping(target = "id", ignore = true)
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    // Thêm hàm updateUser để sửa lỗi "Cannot resolve method" trong UserService
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    // Chuyển từ String (tên quyền) sang đối tượng Role cho MapStruct
    default Role stringToRole(String value) {
        if (value == null) return null;
        return Role.builder().name(value).build(); // Sử dụng Builder nếu có
    }

    // Chuyển ngược từ Role sang String để hiển thị ở UserResponse
    default String roleToString(Role value) {
        if (value == null) return null;
        return value.getName();
    }
}