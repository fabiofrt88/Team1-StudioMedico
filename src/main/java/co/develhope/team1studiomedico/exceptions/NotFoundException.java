package co.develhope.team1studiomedico.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Resource not found");
    }

}
