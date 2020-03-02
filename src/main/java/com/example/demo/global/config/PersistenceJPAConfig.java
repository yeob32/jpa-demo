package com.example.demo.global.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
@Configuration
@EnableTransactionManagement
public class PersistenceJPAConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws SQLException {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.example.demo.domain");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
//        em.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
//        em.afterPropertiesSet();

        return em;
    }

    @Bean
    public DataSource dataSource() throws SQLException {

        Server server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-ifNotExists", "-tcpPort", "8082").start();
        if(server.isRunning(true)) {
            log.info("server run !!");
        }

        log.info("h2 server status = {}", server.getStatus());

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("org.h2.Driver");
        hikariConfig.setJdbcUrl("jdbc:h2:tcp://localhost:8082/./japDemo");
        hikariConfig.setUsername("sa");
        hikariConfig.setPassword("");

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

//    @Repository 어노테이션이 붙어있는 스프링 빈에 예외 변환 AOP를 적용한다.
//    AOP는 JPA 예외를 스프링 프레임워크가 추상화한 예외로 변환한다.
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.use_sql_comments", "true");
//        properties.setProperty("hibernate.id.new_generator_mappings", "true"); // ~ 2.0 default true
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        return properties;
    }
}