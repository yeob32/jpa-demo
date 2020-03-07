package com.example.demo.domain.model3.order.service;

import com.example.demo.domain.model3.delivery.Delivery;
import com.example.demo.domain.model3.item.Item;
import com.example.demo.domain.model3.item.service.ItemService;
import com.example.demo.domain.model3.member.Member;
import com.example.demo.domain.model3.member.MemberRepository;
import com.example.demo.domain.model3.order.Order;
import com.example.demo.domain.model3.order.OrderItem;
import com.example.demo.domain.model3.order.OrderSearch;
import com.example.demo.domain.model3.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    final OrderRepository orderRepository;
    final MemberRepository memberRepository;
    final ItemService itemService;

    public OrderService(OrderRepository orderRepository, MemberRepository memberRepository, ItemService itemService) {
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.itemService = itemService;
    }

    public Long order(Long memberId, Long itemId, int count) {

        Member member = memberRepository.findById(memberId).orElse(null);
        Item item = itemService.findItem(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.getOne(orderId);
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch.toSpecification());
    }

}
