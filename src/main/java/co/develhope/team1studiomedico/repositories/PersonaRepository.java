package co.develhope.team1studiomedico.repositories;

import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface PersonaRepository<T extends PersonaEntity> extends JpaRepository<T, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE #{#entityName} e SET e.status = co.develhope.team1studiomedico.entities.EntityStatusEnum.DELETED WHERE e.id = :id")
    void softDeleteById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE #{#entityName} e SET e.status = co.develhope.team1studiomedico.entities.EntityStatusEnum.DELETED " +
            "WHERE status = co.develhope.team1studiomedico.entities.EntityStatusEnum.ACTIVE")
    void softDelete();

    @Transactional
    @Modifying
    @Query(value = "UPDATE #{#entityName} e SET e.status = co.develhope.team1studiomedico.entities.EntityStatusEnum.ACTIVE WHERE e.id = :id")
    void restoreById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE #{#entityName} e SET e.status = co.develhope.team1studiomedico.entities.EntityStatusEnum.ACTIVE " +
            "WHERE status = co.develhope.team1studiomedico.entities.EntityStatusEnum.DELETED")
    void restore();

    @Transactional
    @Modifying
    @Query(value = "UPDATE #{#entityName} e SET e.status = :status WHERE e.id = :id")
    void changeStatusById(@Param("status") EntityStatusEnum status, @Param("id") Long id);

}
