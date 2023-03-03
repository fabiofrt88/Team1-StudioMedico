package co.develhope.team1studiomedico.entities.utils;

import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import jakarta.persistence.AttributeConverter;

/**
 * Classe di utilità che converte un tipo enum EntityStatusEnum nel tipo Character del relativo status
 * in fase di serializzazione / deserializzazione mediante interfaccia AttributeConverter
 */
public class EntityStatusEnumConverter implements AttributeConverter<EntityStatusEnum, Character> {

    /**
     *
     * @param entityStatusEnum enum EntityStatusEnum (input)
     * @return il relativo status di tipo Character
     */
    @Override
    public Character convertToDatabaseColumn(EntityStatusEnum entityStatusEnum) {
        if(entityStatusEnum == EntityStatusEnum.DELETED) {
            return EntityStatusEnum.DELETED.getStatus();
        }
        return EntityStatusEnum.ACTIVE.getStatus();
    }

    /**
     *
     * @param character carattere associato a uno status di EntityStatusEnum
     * @return il relativo enum EntityStatusEnum
     */
    @Override
    public EntityStatusEnum convertToEntityAttribute(Character character) {
        if(character == EntityStatusEnum.DELETED.getStatus()) {
            return EntityStatusEnum.DELETED;
        }
        return EntityStatusEnum.ACTIVE;
    }

}
