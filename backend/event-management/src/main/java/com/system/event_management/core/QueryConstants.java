package com.system.event_management.core;

public class QueryConstants {

    public static final String IS_USER_EXISTS="SELECT COUNT(u) FROM UserEntity u WHERE u.username = :username";


    public static final String FIND_BY_USERNAME="SELECT u FROM UserEntity u WHERE u.username = :username";

}
