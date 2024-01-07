package com.example.demo.domain.service;

import com.example.demo.domain.dto.CartDto;
import com.example.demo.domain.entity.Cart;
import com.example.demo.domain.entity.ImageBoard;
import com.example.demo.domain.entity.ImageBoardFileInfo;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.CartRepository;
import com.example.demo.domain.repository.ImageBoardFileInfoRepository;
import com.example.demo.domain.repository.ImageBoardRepository;
import com.example.demo.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ImageBoardRepository imageBoardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageBoardFileInfoRepository imageBoardFileInfoRepository;

    @Transactional(rollbackFor = Exception.class)
    public boolean addCart(CartDto dto) {
        // 제품정보(imageBoard) 가져오기
        ImageBoard imageBoard = imageBoardRepository.findById(dto.getProduct_id()).get();
        // 사용자정보(username) 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findById(username).get();

        List<ImageBoardFileInfo> list = imageBoardFileInfoRepository.findByImageBoardId(dto.getProduct_id());
        // Cart 생성
        Cart cart = new Cart();
        cart.setImageBoard(imageBoard);
        cart.setUser(user);
        cart.setRegdate(LocalDateTime.now());
        cart.setAmount(dto.getAmount());
        cart.setColor(dto.getColor());
        cart.setImageBoardFileInfo(list.get(0));
        // Cart 내용저장
        // CartDB 저장

        cartRepository.save(cart);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Cart> getMyCartItems() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return cartRepository.findByUserUsername(username);
    }
}
