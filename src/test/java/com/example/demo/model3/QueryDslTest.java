package com.example.demo.model3;

import com.example.demo.domain.model3.item.Book;
import com.example.demo.domain.model3.item.Item;
import com.example.demo.domain.model3.item.QItem;
import com.example.demo.domain.model3.item.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class QueryDslTest extends Model3Test {

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void querydsl() {
        Item book = new Book();
        book.setName("장난감1");
        book.setPrice(15000);
        book.setStockQuantity(10);
        itemRepository.save(book);

        QItem item = QItem.item;
        Iterable<Item> result = itemRepository.findAll(
                item.name.contains("장난감")
                        .and(item.price.between(10000, 20000))
        );


        Assertions.assertTrue(result.iterator().hasNext());
        Assertions.assertSame(result.iterator().next(), book);

        Iterable<Item> result2 = itemRepository.findAll(
                item.name.contains("장난감")
                        .and(item.price.between(100, 9000))
        );
        Assertions.assertFalse(result2.iterator().hasNext());
    }
}
