package com.example.mp.dto;

import com.example.mp.entity.RoleEntity;
import com.example.mp.entity.UserEntity;
import lombok.Data;

@Data
public class UserRoleDto {
    private Long userId;
    private Long roleId;
}
