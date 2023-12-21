package com.example.demo.controller;

import com.example.demo.domain.dto.CertificationDto;
import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.Authenticator;
import java.util.Arrays;

@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/myinfo")
    public void user(Authentication authentication, Model model) {
        log.info("GET /user/myinfo...Authentication : " + authentication);
        log.info("username : " + authentication.getName());
        log.info("principal : " + authentication.getPrincipal());
        log.info("authorities : " + authentication.getAuthorities());
        log.info("details :  " + authentication.getDetails());
        log.info("credentials : " + authentication.getCredentials());

        model.addAttribute("authentication", authentication);

    }

    @GetMapping("/join")
    public void getJoin() {
        log.info("GET /join");
    }

    @PostMapping("/join")
    public String postJoin(UserDto dto) {
        log.info("POST /join...dto " + dto);
        //파라미터 받기
        //입력값 검증(유효성체크)
        //서비스 실행
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        boolean isJoin = userService.memberJoin(dto);
        //View로 속성등등 전달
        if (isJoin)
            return "redirect:login?msg=MemberJoin Success!";
        else
            return "forward:join?msg=Join Failed....";
        //+a 예외처리

    }

    @GetMapping("/certification")
    public void getCertification(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getCookies() != null) {
            boolean isExisted = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("importAuth")).findFirst()
                    .isEmpty();
            if(!isExisted) {
                response.sendRedirect("/user/join");
            }
        } else {
            log.info("GET /user/certification");
        }
    }

//    @GetMapping("/certification")
//    public String certification(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        log.info("GET /user/certification");
//
//        if(request.getCookies() !=null) {
//            boolean isExisted = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("importAuth")).findFirst()
//                    .isEmpty();
//            if (!isExisted) {
//                response.sendRedirect("/user/join");
//                return null;
//            }
//        }
//        return "user/certification";
//    }

    @PostMapping(value = "/certification", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONObject> postCertification(@RequestBody CertificationDto params,
                                                        HttpServletResponse response) {
        log.info("POST /user/certification.." + params);
        // 쿠키로 본인인증 완료 값을 전달!
        Cookie authCookie = new Cookie("importAuth", "true");
        authCookie.setMaxAge(15 * 60); // 15분 뒤에 쿠키 사라짐. 15 * 60 sec
        authCookie.setPath("/"); // 쿠키가 적용되는 url
        response.addCookie(authCookie);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);

        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    @GetMapping("/findId")
    public void getFindId() {
        log.info("GET /user/findId");

    }

    @GetMapping("/findPw")
    public void getFindPw() {
        log.info("GET /user/findPw");
    }
}
