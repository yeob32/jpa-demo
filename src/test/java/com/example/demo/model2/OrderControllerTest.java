package com.example.demo.model2;

import com.example.demo.domain.model2.enums.OrderStatus;
import com.example.demo.domain.model2.item.Book;
import com.example.demo.domain.model2.item.Item;
import com.example.demo.domain.model2.member.Member;
import com.example.demo.domain.model2.model.Address;
import com.example.demo.domain.model2.order.Order;
import com.example.demo.domain.model2.order.OrderSearch;
import com.example.demo.domain.model2.order.controller.OrderController;

import com.example.demo.domain.model2.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DataJpaTest(properties = {
        "spring.jpa.properties.hibernate.format_sql=true",
        "spring.jpa.properties.hibernate.use_sql_comments=true"
})
@ComponentScan({"com.example.demo.domain.model2"})
@EntityScan("com.example.demo.domain.model2")
public class OrderControllerTest {

    @Autowired
    OrderController orderController;
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    OrderService orderService;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        createMember();
        createBook("test item", 10000, 10);
    }

    @Test
    @DisplayName("주문")
    public void order() throws Exception {

        Member member = entityManager.find(Member.class, 1L);
        Item item = entityManager.find(Item.class, 2L); // 시퀀스 전략 지정해줘야함

        mockMvc.perform(post("/order")
                .param("memberId", "1")
                .param("itemId", "2")
                .param("count", "1"))
                .andDo(print())
                .andExpect(status().isOk());

        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberName(member.getName());
        orderSearch.setOrderStatus(OrderStatus.ORDER);
        List<Order> order = orderService.findOrders(orderSearch);

        assertAll("order", () -> {
            assertFalse(order.isEmpty());
            assertSame(order.get(0).getOrderStatus(), OrderStatus.ORDER);
            assertSame(order.get(0).getMember(), member);
            assertEquals(order.get(0).getOrderItems().size(), 1);
            assertSame(order.get(0).getOrderItems().get(0).getItem(), item);
            assertEquals(order.get(0).getDelivery().getAddress().getCity(), "서울");
        });
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
