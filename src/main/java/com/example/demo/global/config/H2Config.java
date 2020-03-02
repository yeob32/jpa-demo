package com.example.demo.global.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import java.sql.SQLException;

//@Slf4j
//@Configuration
//@ConfigurationProperties(value = "spring.datasource.hikari")
public class H2Config extends HikariConfig {

//    @Bean
//    public DataSource dataSource() throws SQLException {
//
//        Server server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-ifNotExists", "-tcpPort", "8082").start();
//        if(server.isRunning(true)) {
//            log.info("server run !!");
//        }
//
//        log.info("h2 server status = {}", server.getStatus());
//
//        return new HikariDataSource(this);
//    }
}
