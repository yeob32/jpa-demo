package com.example.demo.domain.model1.category;

import com.example.demo.domain.model1.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CATEGORY")
@NoArgsConstructor
@Setter
@Getter
@ToString(exclude = "child")
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
    )
    private List<Item> items = new ArrayList<>();

    public void addChildCategory(Category category) {
        this.child.add(category);
        category.setParent(this);
    }

    public void addItem(Item item) {
        this.items.add(item);
    }
}
