package com.example.demo.domain.model1.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter
@Setter
@ToString
public class Book extends Item {

    private String author;
    private String isbn;
}
