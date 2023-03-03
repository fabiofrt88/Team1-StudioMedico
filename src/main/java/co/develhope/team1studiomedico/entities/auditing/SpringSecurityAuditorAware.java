package co.develhope.team1studiomedico.entities.auditing;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * Classe che rappresenta e istanzia il soggetto auditor che realizzerà le attività di auditing sulle entità del sistema
 */
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("developer");
    }

}
