package com.example.demo.domain.model3.order.custom;

import com.example.demo.domain.model3.order.Order;
import com.example.demo.domain.model3.order.OrderSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomOrderRepository {
    public List<Order> search(OrderSearch orderSearch);
}
