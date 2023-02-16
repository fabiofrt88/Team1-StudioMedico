package co.develhope.team1studiomedico.repositories;


import co.develhope.team1studiomedico.entities.Paziente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RepositoryPaziente extends JpaRepository<Paziente, Long> {
}
