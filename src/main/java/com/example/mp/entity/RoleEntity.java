package com.example.mp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Role")
@NoArgsConstructor
@Data
public class RoleEntity {
    @Id
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;
}
