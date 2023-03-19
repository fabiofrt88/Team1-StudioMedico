package co.develhope.team1studiomedico.dto;

public class ResponseSuccessDTO {

    private final boolean success;
    private String message;

    public ResponseSuccessDTO(String message) {
        this.success = true;
        this.message = message;
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

}
