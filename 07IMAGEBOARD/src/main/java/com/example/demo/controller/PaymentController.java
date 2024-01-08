package com.example.demo.controller;

import com.example.demo.domain.entity.Cart;
import com.example.demo.domain.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private CartService cartService;

    @GetMapping("/read")
    public void getRead(Long[] id_arr, Model model) {
        log.info("GET /payment/read...");
        List<Cart> list = cartService.getMyCartItems(id_arr);
        model.addAttribute("list", list);

        int totalPrice = 0;
        for(Cart cart : list) {
            totalPrice += cart.getAmount() * Integer.parseInt(cart.getImageBoard().getPrice());
        }
        model.addAttribute("totalPrice", totalPrice);

        List<Long> cart_id_list = new ArrayList<>();
        for(Long cart_id : id_arr) {
            cart_id_list.add(cart_id);
        }
        model.addAttribute("cart_id_list", cart_id_list);
    }
}
