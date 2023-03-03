package co.develhope.team1studiomedico.repositories;

import co.develhope.team1studiomedico.entities.PrenotazioneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * L'interfaccia PrenotazioneRepository è un repository che fornisce un insieme di metodi e custom query per la
 * gestione dei dati nel database utilizzando la JPA (Java Persistence API) per definire query ad alto livello.
 */
@Repository
public interface PrenotazioneRepository extends JpaRepository<PrenotazioneEntity, Long> {

}
