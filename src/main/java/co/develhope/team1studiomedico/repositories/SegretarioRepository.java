package co.develhope.team1studiomedico.repositories;

import co.develhope.team1studiomedico.entities.Segretario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SegretarioRepository extends JpaRepository<Segretario, Long> {
}
