package com.springboot.Java.mapper;

import com.springboot.Java.dto.request.RoleRequest;
import com.springboot.Java.dto.response.RoleResponse;
import com.springboot.Java.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target="permissions",ignore=true)
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
}
