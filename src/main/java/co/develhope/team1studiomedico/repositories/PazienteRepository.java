package co.develhope.team1studiomedico.repositories;

import co.develhope.team1studiomedico.entities.PazienteEntity;
import org.springframework.stereotype.Repository;

/**
 * L'interfaccia PazienteRepository è un repository che realizza la logica di persistenza dei dati di PazienteEntity,
 * estende la superInterface PersonaRepository e ne eredita metodi e
 * custom query (query personalizzate che si utilizzano per azioni più specifiche)
 */
@Repository
public interface PazienteRepository extends PersonaRepository<PazienteEntity> {

}
