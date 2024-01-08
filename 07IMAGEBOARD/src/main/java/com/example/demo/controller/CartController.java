package com.example.demo.controller;

import com.example.demo.domain.dto.CartDto;
import com.example.demo.domain.entity.Cart;
import com.example.demo.domain.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/read")
    public void read(Model model) throws Exception {
        log.info("GET /cart/read..");
        List<Cart> list = cartService.getMyCartItems();

        model.addAttribute("list", list);
    }

    @GetMapping("/add")
    @ResponseBody
    public void add(CartDto dto) {
        log.info("GET /cart/add... dto " + dto);

        boolean isok = cartService.addCart(dto);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public void delete(Long cart_id) throws Exception {
        log.info("DELETE /delete id : " + cart_id);
        boolean isDeleted = cartService.deleteCart(cart_id);
    }
}
