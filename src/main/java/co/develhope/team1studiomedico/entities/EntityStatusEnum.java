package co.develhope.team1studiomedico.entities;

public enum EntityStatusEnum {

    ACTIVE('A'),
    DELETED('D');

    private final Character status;

    EntityStatusEnum(Character status) {
        this.status = status;
    }

    public Character getStatus() {
        return status;
    }

}
