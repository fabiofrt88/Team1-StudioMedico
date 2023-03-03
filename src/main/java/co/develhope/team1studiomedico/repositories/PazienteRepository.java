package co.develhope.team1studiomedico.repositories;

import co.develhope.team1studiomedico.entities.PazienteEntity;
import org.springframework.stereotype.Repository;
/**
 * La subInterface PazienteRepository estende la superInterface PersonaRepository e ne acquisisce metodi e
 * custom query (query personalizzate che si utilizzano per azioni più specifiche)
 */
@Repository
public interface PazienteRepository extends PersonaRepository<PazienteEntity> {

}
