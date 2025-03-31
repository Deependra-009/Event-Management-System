package com.system.event_management.core;

public class UserConstants {

    // Success Messages
    public static final String USER_REGISTER_SUCCESS = "User registered successfully";
    public static final String LOGIN_SUCCESS = "Login successful";

    // Error Messages
    public static final String USER_NOT_FOUND = "User with ID %s not found";
    public static final String USER_ALREADY_EXISTS = "User with username %s already exists";

    // Token & Authentication
    public static final String USER_UNAUTHORIZED = "Unauthorized access! Please log in";
    public static final String INVALID_CREDENTIALS = "Invalid email or password";

    // JWT & Authentication messages
    public static final String TOKEN_INVALID = "Invalid authentication token";
    public static final String UNAUTHORIZED_ACCESS = "You are not authorized to access this resource";

}
