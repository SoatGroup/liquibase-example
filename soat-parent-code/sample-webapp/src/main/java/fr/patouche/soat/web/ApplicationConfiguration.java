package fr.patouche.soat.web;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Root application configuration.
 *
 * @author : patouche - 06/10/15.
 */
@Configuration
@EnableJpaRepositories(basePackages = "fr.patouche.soat.repository")
@ComponentScan({ "fr.patouche.soat.service" })
public class ApplicationConfiguration {

    private Map<String, ?> additionalProperties() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        // Auditing : http://docs.jboss.org/hibernate/orm/4.1/devguide/en-US/html/ch15.html#d5e3910
        // properties.put(EnversSettings.AUDIT_TABLE_SUFFIX, "_REVISION");
        // properties.put(EnversSettings.AUDIT_STRATEGY, DefaultAuditStrategy.class.getName());
        // properties.put(EnversSettings.REVISION_FIELD_NAME, "REV_NUMBER");
        // properties.put(EnversSettings.REVISION_TYPE_FIELD_NAME, "REV_ENTITY");
        // properties.put(EnversSettings.REVISION_LISTENER, CustomRevisionListener.class.getName());

        return properties;
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource) {

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setShowSql(false);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("fr.patouche.soat.entity");
        factory.setDataSource(dataSource);
        factory.afterPropertiesSet();
        factory.setJpaPropertyMap(additionalProperties());

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }

}
