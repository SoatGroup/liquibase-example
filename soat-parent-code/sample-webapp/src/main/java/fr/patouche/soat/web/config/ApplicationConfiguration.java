package fr.patouche.soat.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import fr.patouche.soat.repository.config.DataSourceConfiguration;
import fr.patouche.soat.repository.config.SpringDataJpaConfiguration;

/**
 * Root application configuration.
 *
 * @author : patouche - 06/10/15.
 */
@Configuration
@Import({
        DataSourceConfiguration.class,
        SpringDataJpaConfiguration.class
})
@ComponentScan(
        value = "fr.patouche.soat.service",
        excludeFilters = { @ComponentScan.Filter(classes = Configuration.class) }
)
public class ApplicationConfiguration {

}
