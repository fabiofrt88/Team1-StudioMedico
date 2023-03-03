package co.develhope.team1studiomedico.repositories;

import co.develhope.team1studiomedico.entities.SegretarioEntity;
import org.springframework.stereotype.Repository;
/**
 * La subInterface SegretarioRepository estende la superInterface PersonaRepository e ne acquisisce metodi e
 * custom query (query personalizzate che si utilizzano per azioni più specifiche)
 */
@Repository
public interface SegretarioRepository extends PersonaRepository<SegretarioEntity> {

}
