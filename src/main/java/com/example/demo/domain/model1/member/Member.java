package com.example.demo.domain.model1.member;

import com.example.demo.domain.model1.model.Address;
import com.example.demo.domain.model1.model.BaseEntity;
import com.example.demo.domain.model1.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@SequenceGenerator(name = "MEMBER_SEQ_GENERATOR", sequenceName = "MEMBER_SEQ")
@NoArgsConstructor
@Getter
@Setter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
