package com.example.demo.model1;

import com.example.demo.domain.model1.item.Book;
import com.example.demo.domain.model1.item.Item;
import com.example.demo.domain.model1.category.Category;
import com.example.demo.domain.model1.delivery.Delivery;
import com.example.demo.domain.model1.member.Member;
import com.example.demo.domain.model1.order.Order;
import com.example.demo.domain.model1.order.OrderItem;
import com.example.demo.domain.model1.enums.DeliveryStatus;
import com.example.demo.domain.model1.enums.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ModelTest {

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    public void setup() {
        Member member = new Member();
        member.setName("테스트");
        entityManager.persist(member);

        Book book = new Book();
        book.setName("상품1");
        book.setPrice(1000);
        book.setStockQuantity(10);
        book.setAuthor("whoami");
        book.setIsbn("whatisISBN");
        entityManager.persist(book);

        Category category1 = new Category();
        category1.setName("카테고리1");

        Category category2 = new Category();
        category2.setName("카테고리1-1");
        category2.addItem(book);

        category1.addChildCategory(category2);

        Category category3 = new Category();
        category3.setName("카테고리1-2");
        category3.addItem(book);

        category1.addChildCategory(category3);

        book.setCategories(category1.getChild());

        entityManager.persist(category1);
        entityManager.persist(category2);
        entityManager.persist(category3);
    }

    @Test
    @DisplayName("테스트 데이터 조회")
    public void findDummy() {
        TypedQuery<Member> query = entityManager.createQuery("select m from Member m", Member.class);
        List<Member> members = query.getResultList();
        Assertions.assertEquals(members.size(), 1);

        Item item = entityManager.find(Item.class, 1L);
        Assertions.assertEquals(item.getName(), "상품1");

        TypedQuery<Category> categoryQuery = entityManager.createQuery("select c from Category c", Category.class);
        List<Category> categories = categoryQuery.getResultList();

        Assertions.assertEquals(categories.get(0).getName(), "카테고리1");
        Assertions.assertNotNull(categories.get(0).getChild());
        Assertions.assertEquals(categories.get(1).getName(), "카테고리1-1");
        Assertions.assertSame(categories.get(1).getParent(), categories.get(0));
        Assertions.assertTrue(categories.get(1).getChild().isEmpty());
        Assertions.assertEquals(categories.get(2).getName(), "카테고리1-2");
        Assertions.assertSame(categories.get(2).getParent(), categories.get(0));
        Assertions.assertTrue(categories.get(2).getChild().isEmpty());

        Assertions.assertEquals(item.getCategories().size(), 2);
    }

    @Test
    @DisplayName("주문 테스트")
    public void order() {
        Date now = new Date();
        Member member = entityManager.find(Member.class, 1L);
        Item item = entityManager.find(Item.class, 1L);

        OrderItem orderItem = new OrderItem();
        orderItem.setCount(1);
        orderItem.setItem(item);
        orderItem.setOrderPrice(item.getPrice());

        entityManager.persist(orderItem);

        Delivery delivery = new Delivery();
        delivery.setDeliveryStatus(DeliveryStatus.READY);

        entityManager.persist(delivery);

        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        order.addOrderItem(orderItem);
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(now);

        entityManager.persist(order);

        Assertions.assertEquals(member.getOrders().size(), 1);
        Assertions.assertEquals(member.getOrders().get(0), order);
        Assertions.assertSame(member.getOrders().get(0).getMember(), member);
        Assertions.assertSame(member.getOrders().get(0).getDelivery(), delivery);
        Assertions.assertEquals(member.getOrders().get(0).getOrderDate().getTime(), now.getTime());
        Assertions.assertEquals(member.getOrders().get(0).getOrderStatus(), OrderStatus.ORDER);
        Assertions.assertSame(member.getOrders().get(0).getOrderItems().get(0).getItem(), item);
        Assertions.assertSame(member.getOrders().get(0).getOrderItems().get(0).getItem().getName(), item.getName());

        OrderItem findOrderItem = entityManager.find(OrderItem.class, 1L);
        Assertions.assertSame(findOrderItem.getItem(), item);
        Assertions.assertSame(findOrderItem.getItem(), item);
        Assertions.assertEquals(((Book)findOrderItem.getItem()).getAuthor(), "whoami");

        Order findOrder = entityManager.find(Order.class, 1L);
        Assertions.assertSame(findOrder.getOrderItems().get(0), findOrderItem);
    }
}
