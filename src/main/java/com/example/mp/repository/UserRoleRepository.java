package com.example.mp.repository;

import com.example.mp.entity.UserRoleEntity;
import com.example.mp.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleId> {
}
