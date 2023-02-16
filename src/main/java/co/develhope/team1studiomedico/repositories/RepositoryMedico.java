package co.develhope.team1studiomedico.repositories;


import co.develhope.team1studiomedico.entities.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryMedico extends JpaRepository<Medico, Long> {
}
