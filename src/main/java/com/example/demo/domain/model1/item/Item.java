package com.example.demo.domain.model1.item;

import com.example.demo.domain.model1.category.Category;
import com.example.demo.domain.model1.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@SequenceGenerator(name = "ITEM_SEQ_GENERATOR", sequenceName = "ITEM_SEQ")
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "categories")
public abstract class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_SEQ_GENERATOR")
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    public List<Category> categories = new ArrayList<>();
}
