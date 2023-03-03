package co.develhope.team1studiomedico.repositories;

import co.develhope.team1studiomedico.entities.MedicoEntity;
import org.springframework.stereotype.Repository;
/**
 * La subInterface MedicoRepository estende la superInterface PersonaRepository e ne acquisisce metodi e
 * custom query (query personalizzate che si utilizzano per azioni più specifiche)
 */
@Repository
public interface MedicoRepository extends PersonaRepository<MedicoEntity> {

}
