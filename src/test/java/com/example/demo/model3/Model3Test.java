package com.example.demo.model3;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@DataJpaTest(properties = {
        "spring.jpa.properties.hibernate.format_sql=true",
        "spring.jpa.properties.hibernate.use_sql_comments=true"
})
@ComponentScan({"com.example.demo.domain.model3"})
@EntityScan({"com.example.demo.domain.model3"})
@EnableJpaRepositories(basePackages = "com.example.demo.domain.model3")
public class Model3Test {
}
