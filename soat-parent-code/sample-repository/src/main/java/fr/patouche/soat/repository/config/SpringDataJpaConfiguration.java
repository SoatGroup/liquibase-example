package fr.patouche.soat.repository.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Spring data jpa configuration.
 *
 * @author : patouche - 10/10/15.
 */
@Configuration
@EnableJpaRepositories(basePackages = "fr.patouche.soat.repository")
public class SpringDataJpaConfiguration {

    private Map<String, ?> additionalProperties(final Environment env) {
        final Map<String, Object> properties = new HashMap<>();
//        properties.put(AvailableSettings.HBM2DDL_AUTO, "create-drop");
        properties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect");
        return properties;
    }

    /**
     * Create the factory to initialize the entity manager.
     *
     * @param dataSource the datasource to use
     * @param env        the current spring env
     * @return the entity manager factory
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource, final Environment env) {

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setShowSql(false);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("fr.patouche.soat.entity");
        factory.setDataSource(dataSource);
        factory.afterPropertiesSet();
        factory.setJpaPropertyMap(additionalProperties(env));

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}
