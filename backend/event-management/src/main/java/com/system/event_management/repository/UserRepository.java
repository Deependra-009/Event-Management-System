package com.system.event_management.repository;

import com.system.event_management.core.QueryConstants;
import com.system.event_management.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {


    @Query(QueryConstants.IS_USER_EXISTS)
    public int isUserAlreadyExist(
            @Param("username") String username
    );

    @Query(QueryConstants.FIND_BY_USERNAME)
    public UserEntity findByUsername(
            @Param("username") String username
    );



}
