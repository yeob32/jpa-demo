package com.example.demo.domain.model3.member;

import com.example.demo.domain.model3.model.Address;
import com.example.demo.domain.model3.model.BaseEntity;
import com.example.demo.domain.model3.order.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
