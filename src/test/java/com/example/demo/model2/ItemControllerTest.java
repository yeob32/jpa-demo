package com.example.demo.model2;

import com.example.demo.domain.model2.item.controller.ItemContoller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ItemControllerTest extends Model2Test {

    @Autowired
    ItemContoller itemContoller;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(itemContoller).build();
    }

    @Test
    @DisplayName("아이템 조회")
    public void items() throws Exception {
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("아이템 생성")
    public void createItem() throws Exception {

        mockMvc.perform(post("/items")
                .param("name", "test item")
                .param("price", "10000")
                .param("stockQuantity", "10")
//                .content(objectMapper.writeValueAsString(dto)) // @RequestBody
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("test item"))
                .andExpect(jsonPath("$.price").value(10000));

        mockMvc.perform(get("/items"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].name").value("test item"));

        mockMvc.perform(put("/items/1")
                .param("name", "modified item")
                .param("price", "200")
                .param("stockQuantity", "7"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.name").value("modified item"))
                .andExpect(jsonPath("$.price").value(200))
                .andExpect(jsonPath("$.stockQuantity").value(7));
    }
}
