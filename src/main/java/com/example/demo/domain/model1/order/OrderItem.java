package com.example.demo.domain.model1.order;

import com.example.demo.domain.model1.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_ITEM")
@SequenceGenerator(name = "ORDER_ITEM_SEQ_GENERATOR", sequenceName = "ORDER_ITEM_SEQ")
@NoArgsConstructor
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_ITEM_SEQ_GENERATOR")
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int orderPrice;
    private int count;
}
