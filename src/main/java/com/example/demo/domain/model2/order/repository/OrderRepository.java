package com.example.demo.domain.model2.order.repository;

import com.example.demo.domain.model1.member.Member;
import com.example.demo.domain.model2.order.Order;
import com.example.demo.domain.model2.order.OrderSearch;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.binding.QuerydslPredicateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {

    @PersistenceContext
    EntityManager entityManager;

    public void save(Order order) {
        entityManager.persist(order);
    }

    public Order findOrder(Long id) {
        return entityManager.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);

        List<Predicate> criteria = new ArrayList<>();

        if(orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        if(StringUtils.hasText(orderSearch.getMemberName())) {
            Join<Order, Member> m = o.join("member", JoinType.INNER);
            Predicate name = cb.like(m.get("name"), "%" +
                    orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = entityManager.createQuery(cq).setMaxResults(1000);

        return query.getResultList();
    }
}
