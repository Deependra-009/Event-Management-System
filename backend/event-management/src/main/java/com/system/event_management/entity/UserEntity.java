package com.system.event_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userID;
    private String fullName;
    private String username;
    private String password;

    @OneToMany(mappedBy = "userEntity",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<RolesEntity> roles;

    @OneToMany(mappedBy = "userEntity",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RSVPEntity> rsvps;

}
