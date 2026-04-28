package com.springboot.Java.service;

import com.springboot.Java.dto.request.RoleRequest;
import com.springboot.Java.dto.response.PermissionResponse;
import com.springboot.Java.dto.response.RoleResponse;
import com.springboot.Java.mapper.RoleMapper;
import com.springboot.Java.repository.PermissionRepository;
import com.springboot.Java.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    public RoleResponse create(RoleRequest request){
        var role = roleMapper.toRole(request);
        var permissions  = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }
    public List<RoleResponse> getAll(){
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }
    public void delete(String role){
        roleRepository.deleteById(role);
    }
}
