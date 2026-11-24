package Generics;

public class BaseError<T> {
    private T data;
    private String message;
    private int httpStatusCode;

    public BaseError(T data, String message, int httpStatusCode) {
        this.data = data;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
