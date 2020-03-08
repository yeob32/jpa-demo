package com.example.demo.model3;

import com.example.demo.domain.model3.item.Book;
import com.example.demo.domain.model3.member.Member;
import com.example.demo.domain.model3.model.Address;
import com.example.demo.global.config.QuerydslConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DataJpaTest(properties = {
        "spring.jpa.properties.hibernate.format_sql=true",
        "spring.jpa.properties.hibernate.use_sql_comments=true"
})
@ComponentScan({"com.example.demo.domain.model3"})
@EntityScan({"com.example.demo.domain.model3"})
@EnableJpaRepositories(basePackages = "com.example.demo.domain.model3")
@Import(QuerydslConfig.class) // import querydsl config
public class Model3Test {

    @PersistenceContext
    EntityManager entityManager;

    @BeforeEach
    public void init() {
        createMember();
        createBook("test item", 10000, 10);
    }

    private Member createMember() {
        Address address = new Address();
        address.setCity("서울");
        address.setStreet("강가");
        address.setZipcode("123-123");

        Member member = new Member();
        member.setName("회원1");
        member.setAddress(address);

        entityManager.persist(member);

        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        entityManager.persist(book);

        return book;
    }
}
