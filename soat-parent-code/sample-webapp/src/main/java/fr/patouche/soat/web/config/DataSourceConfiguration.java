package fr.patouche.soat.web.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * Datasource configuration.
 *
 * @author : patouche - 07/10/15.
 */
@Configuration
public class DataSourceConfiguration {

    /** The class logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfiguration.class);

    @Configuration
    public static class H2DataSourceConfiguration {

        @Bean
        public DataSource dataSource() {
            LOGGER.info("Create a H2 DataSource");
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .build();
        }
    }

}
