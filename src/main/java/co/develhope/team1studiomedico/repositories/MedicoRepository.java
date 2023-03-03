package co.develhope.team1studiomedico.repositories;

import co.develhope.team1studiomedico.entities.MedicoEntity;
import org.springframework.stereotype.Repository;

/**
 * L'interfaccia MedicoRepository è un repository che realizza la logica di persistenza dei dati di MedicoEntity,
 * estende la superInterface PersonaRepository e ne eredita metodi e
 * custom query (query personalizzate che si utilizzano per azioni più specifiche)
 */
@Repository
public interface MedicoRepository extends PersonaRepository<MedicoEntity> {

}
