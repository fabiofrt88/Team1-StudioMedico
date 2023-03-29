package co.develhope.team1studiomedico.configuration;

import co.develhope.team1studiomedico.entities.auditing.SpringSecurityAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Classe di configurazione che abilità l'auditing delle entità del sistema
 */
@Configuration
@EnableJpaAuditing
public class AuditingConfig {

    @Bean
    public SpringSecurityAuditorAware auditorProvider() {
        return new SpringSecurityAuditorAware();
    }

}
