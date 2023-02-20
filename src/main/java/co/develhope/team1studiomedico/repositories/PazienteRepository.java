package co.develhope.team1studiomedico.repositories;

import co.develhope.team1studiomedico.entities.PazienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PazienteRepository extends JpaRepository<PazienteEntity, Long> {

}
