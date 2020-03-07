package com.example.demo.domain.model3.order;

import com.example.demo.domain.model3.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import static com.example.demo.domain.model3.order.OrderSpec.memberNameLike;
import static com.example.demo.domain.model3.order.OrderSpec.orderStatusEq;

@Getter
@Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;

    public Specification<Order> toSpecification() {
        return Specification.where(memberNameLike(memberName))
                .and(orderStatusEq(orderStatus));
    }
}
