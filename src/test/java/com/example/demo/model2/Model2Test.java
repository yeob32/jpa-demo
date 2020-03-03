package com.example.demo.model2;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest(properties = {
        "spring.jpa.properties.hibernate.format_sql=true",
        "spring.jpa.properties.hibernate.use_sql_comments=true"
})
@ComponentScan({"com.example.demo.domain.model2"})
@EntityScan("com.example.demo.domain.model2")
@EnableJpaRepositories(basePackages = "com.example.demo.domain.model2")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class Model2Test {

}
