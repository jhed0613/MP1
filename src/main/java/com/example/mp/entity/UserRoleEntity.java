package com.example.mp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UserRole")
@NoArgsConstructor
@IdClass(UserRoleId.class)
@Data
public class UserRoleEntity {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Id
    @Column(name = "role_id")
    private String roleId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RoleEntity role;
}

