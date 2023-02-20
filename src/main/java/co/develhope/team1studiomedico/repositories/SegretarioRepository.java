package co.develhope.team1studiomedico.repositories;

import co.develhope.team1studiomedico.entities.SegretarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SegretarioRepository extends JpaRepository<SegretarioEntity, Long> {

}
