package co.develhope.team1studiomedico.dto.success;

/**
 * La classe ResponseDataSuccessDTO rappresenta il DTO (Data Transfer Object) delle response di successo,
 * consente di creare degli oggetti di trasferimento dati mediante i quali sarà possibile
 * restituire una selezione dei dati di altri oggetti DTO nel payload delle response
 */
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
