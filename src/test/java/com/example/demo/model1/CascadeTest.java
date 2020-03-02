package com.example.demo.model1;

import com.example.demo.domain.model1.delivery.Delivery;
import com.example.demo.domain.model1.order.Order;
import com.example.demo.domain.model1.order.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("영속성 전이")
public class CascadeTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("영속성 전이 사용 전")
    public void beforeCascade() {
        Delivery delivery = new Delivery();
        entityManager.persist(delivery);

        OrderItem orderItem1 = new OrderItem();
        OrderItem orderItem2 = new OrderItem();
        entityManager.persist(orderItem1);
        entityManager.persist(orderItem2);

        Order order = new Order();
        order.setDelivery(delivery);
        order.addOrderItem(orderItem1);
        order.addOrderItem(orderItem2);
        entityManager.persist(order);
    }

    @Test
    @DisplayName("영속성 전이 사용 후")
    public void afterCascade() {
        Delivery delivery = new Delivery();

        OrderItem orderItem1 = new OrderItem();
        OrderItem orderItem2 = new OrderItem();

        Order order = new Order();
        order.setDelivery(delivery);
        order.addOrderItem(orderItem1);
        order.addOrderItem(orderItem2);

        entityManager.persist(order); // delivery, orderItems 플러시 시점에 영속성 전이

        Order findOrder = entityManager.find(Order.class, 1L);

        assertEquals(findOrder.getOrderItems().size(), 2);
        assertNotNull(findOrder.getDelivery());
    }
}
