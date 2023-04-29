package codes.aditya.dynamodb.model;

public class AppConstants {
    public static final String [] AUTH_WHITELIST ={
            "/auth/**"
    };

    public static final String RUNTIME_ERROR = " Error while processing the request";
    public static final String BAD_REQUEST_ERROR = "Bad request received";
}
