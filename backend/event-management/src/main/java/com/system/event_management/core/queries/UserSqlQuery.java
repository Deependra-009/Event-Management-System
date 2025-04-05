package com.system.event_management.core.queries;

public class UserSqlQuery {

    public static final String IS_USER_EXISTS="SELECT COUNT(u) FROM UserEntity u WHERE u.username = :username";

    public static final String FIND_BY_USERNAME="SELECT u FROM UserEntity u WHERE u.username = :username";

    public static final String FIND_ALL_USERS_DATA="SELECT u FROM UserEntity u LEFT JOIN FETCH u.roles r LEFT JOIN FETCH u.rsvps rv";

}
