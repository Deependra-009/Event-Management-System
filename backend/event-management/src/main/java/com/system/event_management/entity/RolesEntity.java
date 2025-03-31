package com.system.event_management.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;

    private String role;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity userEntity;

}
