package PDA.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {"PDA.jpa"})
@ActiveProfiles("test")
public class JPASpringTest {

    // TODO: look at spring profiles to get specific application.properties files
    @Value("${spring.datasource.url}")
    String dsUrl;

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSource = DataSourceBuilder.create();
        dataSource.driverClassName("org.h2.Driver");
        dataSource.url("application.properties");
        dataSource.username("sa");
        dataSource.password("sa");
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
