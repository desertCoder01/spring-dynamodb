package codes.aditya.dynamodb.model.response;

public class GenericApiResponse<T> {
    private String uri;
    private Integer httpStatusCode;
    private T data;
    private String errorMessage;
    private String errorDetail;
}
