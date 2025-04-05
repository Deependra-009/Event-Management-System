package com.system.event_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long eventId;

    @NotBlank(message = "Event name is required")
    private String eventName;

    @NotBlank(message = "Location is required")
    private String eventLocation;

    @NotNull(message = "Date and time are required")
    private Timestamp eventDateTime;

    @OneToMany(mappedBy = "eventEntity",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonIgnore
    private List<RSVPEntity> rsvps;


}
