package com.springboot.Java.mapper;

import com.springboot.Java.dto.request.PermissionRequest;
import com.springboot.Java.dto.response.PermissionResponse;
import com.springboot.Java.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
