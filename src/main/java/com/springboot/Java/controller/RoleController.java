package com.springboot.Java.controller;

import com.springboot.Java.dto.request.ApiResponse;
import com.springboot.Java.dto.request.PermissionRequest;
import com.springboot.Java.dto.request.RoleRequest;
import com.springboot.Java.dto.response.PermissionResponse;
import com.springboot.Java.dto.response.RoleResponse;
import com.springboot.Java.service.PermissionService;
import com.springboot.Java.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults( level = AccessLevel.PRIVATE,makeFinal = true)
public class RoleController {
    RoleService roleService;


    @PostMapping
    public ApiResponse<RoleResponse> create(@RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAll(){
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }
    @DeleteMapping("/{role}")
    ApiResponse<Void> deleted(@PathVariable String role){
        roleService.delete(role);
        return ApiResponse.<Void>builder().build();
    }


}
