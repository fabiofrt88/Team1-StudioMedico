package co.develhope.team1studiomedico.repositories;

import co.develhope.team1studiomedico.entities.MedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<MedicoEntity, Long> {

}
