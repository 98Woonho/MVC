package com.example.demo.controller;

import com.example.demo.domain.dto.PaymentDto;
import com.example.demo.domain.entity.Cart;
import com.example.demo.domain.entity.Payment;
import com.example.demo.domain.service.CartService;
import com.example.demo.domain.service.PaymentService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.HTMLEditorKit;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

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

    // 결제 취소용 AccessToken 발급요청
    public String getAccessToken() {
        // URL
        // 아임포트 API의 토큰을 얻기 위한 엔드포인트 URL을 지정
        String url = "https://api.iamport.kr/users/getToken";

        // Request Header
        // HTTP 요청 헤더를 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // Request Body
        // HTTP 요청 바디를 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("imp_key", "7582034642764268");
        params.add("imp_secret", "JxMwheK2PKBrxFxOifDLwwZvdyzjwDERKj4TzStgSZ06Wmg3oQp7h3WjK3nOfdjXsSXF0ZNgCbBWyPrV");

        // Header + Body
        // 요청 헤더와 바디를 합쳐서 HttpEntity 객체를 생성
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(params, headers);

        // 요청
        // RestTemplate을 사용하여 HTTP POST 요청을 보냄. 응답은 ImportAccessTokenResponse에 Mapping됨.
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ImportAccessTokenResponse> resp =  restTemplate.exchange(url, HttpMethod.POST,entity,ImportAccessTokenResponse.class);

        // accessToken return
        return resp.getBody().getResponse().getAccess_token();
    }

    // 결제 취소 요청
    @GetMapping("/cancel/{imp_uid}/{pay_id}")
    @ResponseBody
    public void cancel(@PathVariable String imp_uid,
                       @PathVariable String pay_id) {
        // accessToken 받기
        String accessToken = getAccessToken();

        // URL
        // 아임포트 결제 취소를 위한 엔드포인트 URL을 지정
        String url = "https://api.iamport.kr/payments/cancel";

        // Request Header
        // HTTP 요청 헤더를 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/json");

        // Request Body
        // HTTP 요청 바디를 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("imp_uid", imp_uid);

        // Header + Body
        // 요청 헤더와 바디를 합쳐서 HttpEntity 객체를 생성
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(params, headers);

        // 요청
        // RestTemplate을 사용하여 HTTP POST 요청을 보냄. 응답은 String에 Mapping됨.
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // DB 삭제
        paymentService.removePayment(pay_id);

        System.out.println(resp);
    }
}

@Data
class ImportAccessTokenResponse {
    public int code;
    public Object message;
    public Response response;
}

@Data
class Response {
    public String access_token;
    public int now;
    public int expired_at;
}