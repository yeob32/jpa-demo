package com.example.demo.domain.model3.order.custom;

import com.example.demo.domain.model3.member.QMember;
import com.example.demo.domain.model3.order.Order;
import com.example.demo.domain.model3.order.OrderSearch;
import com.example.demo.domain.model3.order.QOrder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class OrderRepositoryImpl extends QuerydslRepositorySupport implements CustomOrderRepository {

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     */
    public OrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> search(OrderSearch orderSearch) {

        QOrder order = QOrder.order;
        QMember member = QMember.member;

        JPQLQuery<Order> jpaQuery = from(order);

        if(StringUtils.hasText(orderSearch.getMemberName())) {
            jpaQuery.leftJoin(order.member, member)
                    .where(member.name.contains(orderSearch.getMemberName()));
        }

        if(orderSearch.getOrderStatus() != null) {
            jpaQuery.where(order.orderStatus.eq(orderSearch.getOrderStatus()));
        }

        return jpaQuery.fetch();
    }
}
