package com.example.demo.domain.model2.item.repository;

import com.example.demo.domain.model2.item.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemRepository {

    @PersistenceContext
    EntityManager entityManager;

    public void save(Item item) {
        // id != null -> 이미 한 번 영속화 되었던 엔티티
        if (item.getId() == null) {
            entityManager.persist(item);
        } else {
            entityManager.merge(item);
        }
        entityManager.persist(item);
    }

    public Item findOne(Long id) {
        return entityManager.find(Item.class, id);
    }

    public List<Item> findAll() {
        return entityManager.createQuery("select i from Item i", Item.class).getResultList();
    }
}
