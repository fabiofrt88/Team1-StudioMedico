package co.develhope.team1studiomedico.entities;

public enum EntityStatusEnum {

    ACTIVE("A"),
    DELETED("D");

    private final String status;

    EntityStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
