package com.example.demo.model2;

import com.example.demo.domain.model2.enums.OrderStatus;
import com.example.demo.domain.model2.item.Book;
import com.example.demo.domain.model2.item.Item;
import com.example.demo.domain.model2.item.exception.NotEnoughStockException;
import com.example.demo.domain.model2.member.Member;
import com.example.demo.domain.model2.model.Address;
import com.example.demo.domain.model2.order.Order;
import com.example.demo.domain.model2.order.repository.OrderRepository;
import com.example.demo.domain.model2.order.service.OrderService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest extends Model2Test {

    private static StringBuilder output = new StringBuilder();

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @org.junit.jupiter.api.Order(1)
    @ParameterizedTest
    @ValueSource(ints = 2)
    @DisplayName("상품 주문")
    public void order(int orderCount) {
        output.append("테");

        // Given
        Member member = createMember();
        Item item = createBook("테스트책", 10000, 10);

        // When
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // Then
        Order getOrder = orderRepository.findOrder(orderId);

        assertSame(OrderStatus.ORDER, getOrder.getOrderStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(10000 * 2, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
        assertEquals(8, item.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
    }

    @org.junit.jupiter.api.Order(2)
    @ParameterizedTest
    @ValueSource(ints = 11)
    @DisplayName("재고 수량 초과")
    public void checkStockQuantity(int orderCount) {
        output.append("스");

        // Given
        Member member = createMember();
        Item item = createBook("테스트책", 10000, 10);

        // When Then
        assertThrows(NotEnoughStockException.class,
                () -> orderService.order(member.getId(), item.getId(), orderCount),
                "재고 수량 부족 예외 발생");
    }

    @org.junit.jupiter.api.Order(3)
    @ParameterizedTest
    @ValueSource(ints = 2)
    @DisplayName("주문 취소")
    public void cancel(int orderCount) {
        output.append("트");

        // Given
        Member member = createMember();
        Item item = createBook("테스트책", 10000, 10);
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // When
        orderService.cancelOrder(orderId);

        // Then
        Order getOrder = orderRepository.findOrder(orderId);

        assertAll("주문 취소",
                () -> {
                    assertSame(getOrder.getOrderStatus(), OrderStatus.CANCEL, "주문 취소시 상태는 CANCEL 이다.");
                    assertEquals(10, item.getStockQuantity(), "주문이 취소된 상푸믕ㄴ 그만큼 재고가 증가한다.");
                });
    }

    @AfterAll
    public static void afterAll() {
        assertEquals(output.toString(), "테스트");
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
