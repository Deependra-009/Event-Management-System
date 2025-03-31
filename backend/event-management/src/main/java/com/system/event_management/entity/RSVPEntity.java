package com.system.event_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RSVPEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rsvpID;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
//    @JsonIgnore
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
//    @JsonIgnore
    private EventEntity eventEntity;

    @Column(nullable = false)
    private boolean attending;

}
