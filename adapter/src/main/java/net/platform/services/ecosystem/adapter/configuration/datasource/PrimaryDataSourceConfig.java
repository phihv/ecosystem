package net.platform.services.ecosystem.adapter.configuration.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@EnableJpaRepositories(
        basePackages = "net.platform.services.ecosystem.adapter.outbound.jpa",
        entityManagerFactoryRef = PrimaryDataSourceConfig.ENTITY_MANAGER_BEAN,
        transactionManagerRef = PrimaryDataSourceConfig.TRANSACTION_MANAGER_BEAN
)
@Configuration
public class PrimaryDataSourceConfig {
    public static final String DATA_SOURCE_BEAN = "primaryDataSource";
    public static final String JPA_PROPERTIES_BEAN = "primaryJpaProperties";
    public static final String ENTITY_MANAGER_BEAN = "primaryEntityManager";
    public static final String TRANSACTION_MANAGER_BEAN = "primaryTransactionManager";
    public static final String[] ENTITY_BASE_PACKAGES = {
            "net.platform.services.ecosystem.adapter.outbound.jpa"
    };

    @Bean
    @ConfigurationProperties("spring.datasource.primary")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(DATA_SOURCE_BEAN)
    @ConfigurationProperties("spring.datasource.primary.hikari")
    public DataSource primaryDataSource(DataSourceProperties primaryDataSourceProperties) {
        return primaryDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(JPA_PROPERTIES_BEAN)
    @ConfigurationProperties("spring.jpa")
    public JpaProperties primaryJpaProperties() {
        return new JpaProperties();
    }

    @Primary
    @Bean(ENTITY_MANAGER_BEAN)
    public LocalContainerEntityManagerFactoryBean primaryEntityManager(
            @Qualifier(DATA_SOURCE_BEAN) DataSource dataSource,
            @Qualifier(JPA_PROPERTIES_BEAN) JpaProperties jpaProperties
    ) {
        var entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan(ENTITY_BASE_PACKAGES);
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        var vendorProperties = new HashMap<String, Object>(jpaProperties.getProperties());

        vendorProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

        entityManagerFactory.setJpaPropertyMap(vendorProperties);
        return entityManagerFactory;
    }

    @Primary
    @Bean(TRANSACTION_MANAGER_BEAN)
    public PlatformTransactionManager primaryTransactionManager(
            @Qualifier(ENTITY_MANAGER_BEAN) LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }
}
