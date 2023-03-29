package co.develhope.team1studiomedico.dto.success;

/**
 * La classe ResponseSuccessDTO rappresenta il DTO (Data Transfer Object) delle response di successo,
 * consente di creare degli oggetti di trasferimento dati mediante i quali sarà possibile
 * restituire dei messaggi di avvenuta creazione / modifica delle risorse nel payload delle response
 */
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
