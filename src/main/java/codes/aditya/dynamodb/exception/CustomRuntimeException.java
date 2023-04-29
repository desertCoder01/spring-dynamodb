package codes.aditya.dynamodb.exception;

public class CustomRuntimeException extends RuntimeException {
    private String message;

    public CustomRuntimeException(String message){
        super(message);
        this.message=message;
    }
}
