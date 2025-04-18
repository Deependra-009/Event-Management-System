package com.system.event_management.core.messages;

public class UserMessages {

    // Success Messages
    public static final String USER_REGISTER_SUCCESS = "User registered successfully";
    public static final String LOGIN_SUCCESS = "Login successful";

    // Error Messages
    public static final String USER_NOT_FOUND = "User with username %s not found";
    public static final String USER_ALREADY_EXISTS = "User with username %s already exists";

    // Token & Authentication
    public static final String USER_UNAUTHORIZED = "Unauthorized access! Please log in";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";

    // JWT & Authentication messages
    public static final String TOKEN_INVALID = "Invalid JWT Token: JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.";
    public static final String TOKEN_EXPIRED = "Token Expired";
    public static final String UNAUTHORIZED_ACCESS = "You are not authorized to access this resource";
    public static final String TOKEN_MISSING_INVALID="Token Missing or Invalid";

}
