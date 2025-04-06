package com.system.event_management.repository;

import com.system.event_management.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity,Long> {

    List<EventEntity> findByUserEntityUserID(Long userId);




}
