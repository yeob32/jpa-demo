package com.example.demo.domain.model2.item.controller;

import com.example.demo.domain.model2.item.Book;
import com.example.demo.domain.model2.item.Item;
import com.example.demo.domain.model2.item.service.ItemService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ItemContoller {

    ItemService itemService;

    public ItemContoller(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping(value = "/items")
    public ResponseEntity<Item> create(@Valid Book item, BindingResult result) {
        if (result.hasErrors()) {

        }

        itemService.saveitem(item);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PutMapping(value = "/items/{itemId}")
    public ResponseEntity<Item> modify(@Valid @PathVariable Long itemId, BookModifyDto dto, BindingResult result) {

        Item findItem = itemService.findItem(itemId);
        findItem.setName(dto.getName());
        findItem.setPrice(dto.getPrice());
        findItem.setStockQuantity(dto.getStockQuantity());

//         Item mergeItem = em.merge(dto.toItem()); // 모든 속성 변경

        return new ResponseEntity<>(findItem, HttpStatus.OK);
    }

    @GetMapping(value = "/items")
    public ResponseEntity<List<Item>> items() {
        return new ResponseEntity<>(itemService.findItems(), HttpStatus.OK);
    }

    @Data
    public static class BookModifyDto {
        private String name;
        private int price;
        private int stockQuantity;
        private String author;
        private String isbn;

        public Item toItem() {
            Item item = new Book();
            item.setName(this.name);
            item.setPrice(this.price);
            item.setStockQuantity(this.stockQuantity);

            return item;
        }
    }
}
