package com.example.config.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = {"com.example.app.poc"},
        entityManagerFactoryRef = PocDatabaseConfiguration.POC_ENTITY_MANAGER_FACTORY,
        transactionManagerRef = PocDatabaseConfiguration.POC_TRANSACTION_MANAGER
)
@EnableTransactionManagement
public class PocDatabaseConfiguration {

    public static final String POC_DATASOURCE = "pocDatasource";
    public static final String POC_TRANSACTION_MANAGER = "pocTransactionManager";
    public static final String POC_ENTITY_MANAGER_FACTORY = "pocEntityManagerFactory";

    @Autowired
    private Environment environment;

    @Bean
    @ConfigurationProperties("application.poc.hikari")
    public HikariConfig pocHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @ConfigurationProperties(value = "application.poc.hibernate")
    public Map<String, String> pocHibernateProperties() {
        return new HashMap<>();
    }

    @Bean
    @Primary
    public HikariDataSource pocDatasource(@Qualifier("pocHikariConfig")HikariConfig pocHikariConfig) {
        HikariDataSource hikariDataSource = new HikariDataSource(pocHikariConfig);
        return hikariDataSource;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean pocEntityManagerFactory(@Qualifier(PocDatabaseConfiguration.POC_DATASOURCE)HikariDataSource pocDatasource,
                                                                            @Qualifier("pocHibernateProperties")Map<String, String> pocHibernateProperties) {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(pocDatasource);
        factoryBean.setPackagesToScan("com.example.app.poc");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaPropertyMap(pocHibernateProperties);
        return factoryBean;
    }

    @Bean
    @Primary
    public PlatformTransactionManager pocTransactionManager(@Qualifier(PocDatabaseConfiguration.POC_ENTITY_MANAGER_FACTORY) EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    @ConfigurationProperties(prefix = "application.poc.liquibase")
    public LiquibaseProperties pocLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase pocLiquibase(@Qualifier("pocLiquibaseProperties")LiquibaseProperties pocLiquibaseProperties,
                                          @Qualifier(PocDatabaseConfiguration.POC_DATASOURCE)HikariDataSource pocDatasource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(pocDatasource);
        liquibase.setChangeLog(pocLiquibaseProperties.getChangeLog());
        liquibase.setContexts(String.join(",", pocLiquibaseProperties.getContexts()));
        liquibase.setDropFirst(pocLiquibaseProperties.isDropFirst());
        return liquibase;
    }
}
