package com.example.demo.model3;

import com.example.demo.domain.model3.enums.OrderStatus;
import com.example.demo.domain.model3.item.Book;
import com.example.demo.domain.model3.item.Item;
import com.example.demo.domain.model3.item.QItem;
import com.example.demo.domain.model3.item.repository.ItemRepository;
import com.example.demo.domain.model3.item.service.ItemService;
import com.example.demo.domain.model3.member.Member;
import com.example.demo.domain.model3.member.MemberService;
import com.example.demo.domain.model3.order.Order;
import com.example.demo.domain.model3.order.OrderSearch;
import com.example.demo.domain.model3.order.custom.CustomOrderRepository;
import com.example.demo.domain.model3.order.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QueryDslTest extends Model3Test {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CustomOrderRepository customOrderRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;
    @Autowired
    OrderService orderService;

    @Test
    @DisplayName("Querydsl 테스트")
    public void querydsl() {
        Item book = new Book();
        book.setName("장난감1");
        book.setPrice(15000);
        book.setStockQuantity(10);
        itemRepository.save(book);

        QItem item = QItem.item;
        Iterable<Item> result = itemRepository.findAll(
                item.name.contains("장난감")
                        .and(item.price.between(10000, 20000))
        );

        Assertions.assertTrue(result.iterator().hasNext());
        Assertions.assertSame(result.iterator().next(), book);

        Iterable<Item> result2 = itemRepository.findAll(
                item.name.contains("장난감")
                        .and(item.price.between(100, 9000))
        );
        Assertions.assertFalse(result2.iterator().hasNext());
    }

    @Test
    @DisplayName("커스텀 리파지토리 테스트")
    public void customRepository() {
        Member member = memberService.findById(1L);
        Item item = itemService.findItem(2L);  // 시퀀스 전략 지정해줘야함

        orderService.order(member.getId(), item.getId(), 1);

        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberName(member.getName());
        orderSearch.setOrderStatus(OrderStatus.ORDER);
        List<Order> order = customOrderRepository.search(orderSearch);

        assertFalse(order.isEmpty());
        assertSame(order.get(0).getOrderStatus(), OrderStatus.ORDER);

        List<Order> order2 = customOrderRepository.searchByJpaFactory(orderSearch);

        assertFalse(order2.isEmpty());
        assertSame(order2.get(0).getOrderStatus(), OrderStatus.ORDER);
    }

}
