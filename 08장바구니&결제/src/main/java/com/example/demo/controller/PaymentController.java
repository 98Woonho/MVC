package com.example.demo.controller;

import com.example.demo.domain.dto.PaymentDto;
import com.example.demo.domain.entity.Cart;
import com.example.demo.domain.entity.Payment;
import com.example.demo.domain.service.CartService;
import com.example.demo.domain.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/payment")
public class PaymentController {
    // -----------------------------------------------------------
    // 결제 조회 관련 내용은 RESTAPI로 요청해야 한다
    // -----------------------------------------------------------
    // DOCUMENT : https://developers.portone.io/api/rest-v1/payment
    // DOCUMENT - AccessToken사용  https://developers.portone.io/docs/ko/api/rest-api-access-token?v=v1#step-03--%ED%86%A0%ED%81%B0-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0
    //
    // 관리자(결제내역확인) : https://classic-admin.portone.io/

    @Autowired
    private CartService cartService;

    @Autowired
    private PaymentService paymentService;

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

        List<Long> cart_id_list =new ArrayList<>();
        for(Long cart_id : id_arr)
            cart_id_list.add(cart_id);
        model.addAttribute("cart_id_list", cart_id_list);

    }

    @GetMapping("/add")
    @ResponseBody
    public void add(PaymentDto dto) throws UnsupportedEncodingException {
        dto.setAddress(URLDecoder.decode(dto.getAddress(), "UTF-8"));
        dto.setName(URLDecoder.decode(dto.getName(), "UTF-8"));
        List<String> n_cart_id = new ArrayList<>();
        for(String cart_id : dto.getCart_id()) {
            n_cart_id.add(URLDecoder.decode(cart_id, "UTF-8").trim());
        }
        dto.setCart_id(n_cart_id);

        boolean isadded = paymentService.addPayment(dto);
    }

    @GetMapping("/list")
    public void list(Model model) throws Exception {
        List<Payment> list = paymentService.getMyPaymentList();
        model.addAttribute("list", list);
    }
}
