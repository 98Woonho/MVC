package com.example.demo.controller;

import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;

@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @GetMapping("/myinfo")
    public void user(Authentication authentication , Model model){
        log.info("GET /user/myinfo...Authentication : " + authentication);
        log.info("username : " + authentication.getName());
        log.info("principal : " + authentication.getPrincipal());
        log.info("authorities : " + authentication.getAuthorities());
        log.info("details :  " +authentication.getDetails());
        log.info("credentials : " + authentication.getCredentials());

        model.addAttribute("authentication",authentication);

    }



    @GetMapping("/join")
    public void getJoin(){
        log.info("GET /join");
    }
    @PostMapping("/join")
    public String postJoin(UserDto dto){
        log.info("POST /join...dto " + dto);
        //파라미터 받기
        //입력값 검증(유효성체크)
        //서비스 실행
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        boolean isJoin =  userService.memberJoin(dto);
        //View로 속성등등 전달
        if(isJoin)
            return "redirect:login?msg=MemberJoin Success!";
        else
            return "forward:join?msg=Join Failed....";
        //+a 예외처리

    }

    @GetMapping("/certification")
    public void getCertification() {
        log.info("GET /user/certification");
    }

    @PostMapping("/certification")
    public void postCertification(@RequestBody String is_auth_join,
                                  @RequestBody String auth_value) {
        log.info("POST /user/certification.." + is_auth_join + "," + auth_value);
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
