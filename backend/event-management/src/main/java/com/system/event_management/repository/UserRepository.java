package com.system.event_management.repository;

import com.system.event_management.core.queries.UserSqlQuery;
import com.system.event_management.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {


    @Query(UserSqlQuery.IS_USER_EXISTS)
    public int isUserAlreadyExist(
            @Param("username") String username
    );

    @Query(UserSqlQuery.FIND_BY_USERNAME)
    public UserEntity findByUsername(
            @Param("username") String username
    );

    @Query(UserSqlQuery.GET_USER_ID_BY_USERNAME)
    public Long fetchUserIdByUsername(
            @Param("username") String username
    );

    @Query(UserSqlQuery.FIND_ALL_USERS_DATA)
    public List<UserEntity> fetchUserWithRolesAndRsvps();



}
