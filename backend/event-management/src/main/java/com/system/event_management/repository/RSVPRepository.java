package com.system.event_management.repository;

import com.system.event_management.entity.RSVPEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RSVPRepository extends JpaRepository<RSVPEntity,Long> {

    boolean existsByUserEntityUserIDAndEventEntityEventId(Long userId, Long eventId);



}
