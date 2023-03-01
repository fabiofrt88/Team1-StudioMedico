package co.develhope.team1studiomedico.entities.utils;

import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import jakarta.persistence.AttributeConverter;

public class EntityStatusEnumConverter implements AttributeConverter<EntityStatusEnum, Character> {

    @Override
    public Character convertToDatabaseColumn(EntityStatusEnum entityStatusEnum) {
        if(entityStatusEnum == EntityStatusEnum.DELETED) {
            return EntityStatusEnum.DELETED.getStatus();
        }
        return EntityStatusEnum.ACTIVE.getStatus();
    }

    @Override
    public EntityStatusEnum convertToEntityAttribute(Character character) {
        if(character == EntityStatusEnum.DELETED.getStatus()) {
            return EntityStatusEnum.DELETED;
        }
        return EntityStatusEnum.ACTIVE;
    }

}
