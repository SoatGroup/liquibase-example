package fr.patouche.soat.web.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import fr.patouche.soat.LiquibaseHelper;

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
    @Profile("!db-jndi")
    public static class H2DataSourceConfiguration {

        /**
         * Create a data source base on H2 database.
         *
         * @return the h2 dataSource
         */
        @Bean
        public DataSource dataSource() {
            LOGGER.info("Create a H2 DataSource");
            return new LiquibaseHelper(new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build())
                    .update()
                    .getDataSource();
        }
    }

    @Configuration
    @Profile("db-jndi")
    public static class JndiDataSourceConfiguration {

        /**
         * Create a data source base on H2 database.
         *
         * @return the h2 dataSource
         */
        @Bean
        public DataSource dataSource() {
            return new LiquibaseHelper(new JndiDataSourceLookup().getDataSource("jdbc/dev"))
                    .checkAndFail()
                    .getDataSource();
        }
    }

}
