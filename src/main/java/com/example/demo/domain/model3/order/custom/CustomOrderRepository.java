package com.example.demo.domain.model3.order.custom;

import com.example.demo.domain.model3.order.Order;
import com.example.demo.domain.model3.order.OrderSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // OrderRepository 에서 상속하면 JpaRepository 기능이랑 같이 쓸 수 있다.
public interface CustomOrderRepository {
    List<Order> search(OrderSearch orderSearch);
    List<Order> searchByJpaFactory(OrderSearch orderSearch);
}
