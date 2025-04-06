package com.system.event_management.repository;

import com.system.event_management.entity.RSVPEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RSVPRepository extends JpaRepository<RSVPEntity,Long> {

    boolean existsByUserEntityUserIDAndEventEntityEventId(Long userId, Long eventId);

    RSVPEntity findByUserEntityUserIDAndEventEntityEventId(Long userId, Long eventId);


}
