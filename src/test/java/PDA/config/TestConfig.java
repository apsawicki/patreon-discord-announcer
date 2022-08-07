package PDA.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableScheduling
@EnableJpaRepositories
public class TestConfig {

//    @Value("${spring.datasource.url}") String dsUrl;
//    @Value("${spring.datasource.username}") String dsUsername;
//    @Value("${spring.datasource.password}") String dsPassword;

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSource = DataSourceBuilder.create();
        dataSource.driverClassName("org.h2.Driver"); // org.postgresql.Driver
        dataSource.url("jdbc:h2:mem:testdb");
        dataSource.username("sa");
        dataSource.password("");
        return dataSource.build();
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }

    private HibernateJpaVendorAdapter vendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        return vendorAdapter;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter());
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
//        entityManagerFactoryBean.setPackagesToScan();
        entityManagerFactoryBean.setJpaProperties(jpaHibernateProperties());

        return entityManagerFactoryBean;
    }

    private Properties jpaHibernateProperties() {
        Properties props = new Properties();
        props.put("hibernate.show_sql", true);
        return props;
    }
}
