package com.example.demo.domain.model1.delivery;

import com.example.demo.domain.model1.model.Address;
import com.example.demo.domain.model1.order.Order;
import com.example.demo.domain.model1.enums.DeliveryStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "DELIVERY")
@Getter
@Setter
@ToString
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "updated_at", nullable = false)
    private Date modifiedAt;
}
