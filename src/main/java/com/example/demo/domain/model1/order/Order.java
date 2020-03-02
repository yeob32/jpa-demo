package com.example.demo.domain.model1.order;

import com.example.demo.domain.model1.delivery.Delivery;
import com.example.demo.domain.model1.member.Member;
import com.example.demo.domain.model1.enums.OrderStatus;
import com.example.demo.domain.model1.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@SequenceGenerator(name = "ORDER_SEQ_GENERATOR", sequenceName = "ORDER_SEQ")
@NoArgsConstructor
@Getter
@Setter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_SEQ_GENERATOR")
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void setMember(Member member) {
        if(this.member != null) {
            this.member.getOrders().remove(this);
        }

        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
