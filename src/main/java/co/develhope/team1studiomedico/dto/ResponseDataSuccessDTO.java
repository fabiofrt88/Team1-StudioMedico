package co.develhope.team1studiomedico.dto;

public class ResponseDataSuccessDTO<T> {

    private final boolean success;
    private String message;
    private T data;

    public ResponseDataSuccessDTO(String message) {
        this.success = true;
        this.message = message;
    }

    public ResponseDataSuccessDTO(String message, T data) {
        this.success = true;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
