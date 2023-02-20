package co.develhope.team1studiomedico.repositories;

import co.develhope.team1studiomedico.entities.PrenotazioneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrenotazioneRepository extends JpaRepository<PrenotazioneEntity, Long> {

}
