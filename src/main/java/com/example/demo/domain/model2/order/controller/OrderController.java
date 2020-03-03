package com.example.demo.domain.model2.order.controller;

import com.example.demo.domain.model2.item.service.ItemService;
import com.example.demo.domain.model2.member.MemberService;
import com.example.demo.domain.model2.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class OrderController {

    OrderService orderService;
    MemberService memberService;
    ItemService itemService;

    public OrderController(OrderService orderService, MemberService memberService, ItemService itemService) {
        this.orderService = orderService;
        this.memberService = memberService;
        this.itemService = itemService;
    }

    @PostMapping(value = "/order")
    @ResponseStatus(value = HttpStatus.OK, code = HttpStatus.OK, reason = "order completed")
    public void order(@RequestParam("memberId") Long memberId, @RequestParam("itemId") Long itemId, @RequestParam("count") int count) {
        orderService.order(memberId, itemId, count);
    }
}
