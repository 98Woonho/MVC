package com.example.demo.controller;

import com.example.demo.domain.dto.ImageBoardDto;
import com.example.demo.domain.service.ImageBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@RequestMapping("/imageboard")
public class ImageBoardController {
    @Autowired
    private ImageBoardService imageBoardService;

    @GetMapping("/add")
    public void add(){
        log.info("GET /imageBoard/add");
    }

    @PostMapping("/add")
    public void add_post(ImageBoardDto dto) throws Exception {
        log.info("POST /imageBoard/add files : " + dto);

        // 서비스 실행
        boolean isuploaded = imageBoardService.addImageContents(dto);
    }
}
