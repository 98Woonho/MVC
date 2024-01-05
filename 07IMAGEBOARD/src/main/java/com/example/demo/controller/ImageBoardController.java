package com.example.demo.controller;

import com.example.demo.domain.dto.ImageBoardDto;
import com.example.demo.domain.entity.ImageBoard;
import com.example.demo.domain.entity.ImageBoardFileInfo;
import com.example.demo.domain.service.ImageBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    @ResponseBody
    public ResponseEntity<String> add_post(ImageBoardDto dto) throws Exception {
        log.info("POST /imageBoard/add files : " + dto);

        // 서비스 실행
        boolean isuploaded = imageBoardService.addImageContents(dto);

        // 뷰 처리
        if(isuploaded) {
            return new ResponseEntity("message", HttpStatus.OK);
        } else {
            return new ResponseEntity("fail...", HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/list")
    public void list(Model model) throws Exception {
        log.info("GET /imageboard/list");
        List<ImageBoardFileInfo> fileList = imageBoardService.getAllItems();
        // ImageBoard의 id를 기준으로 중복을 제거하여 Map을 생성합니다.
        Map<Long, ImageBoardFileInfo> uniqueItemsById = fileList.stream()
                .collect(Collectors.toMap(item -> item.getImageBoard().getId(), Function.identity(), (existing, replacement) -> existing));
        // 중복이 제거된 값들을 다시 List로 변환합니다.
        List<ImageBoardFileInfo> uniqueFileList = uniqueItemsById.values().stream().collect(Collectors.toList());

        // 결과를 출력합니다.
        uniqueFileList.forEach(item -> System.out.println(item));

        model.addAttribute("list",uniqueFileList);
    }

    @GetMapping("/read")
    public void read(Long id, Model model) throws Exception {
        List<ImageBoardFileInfo> list = imageBoardService.getImageBoardItem(id);

        model.addAttribute("list", list);
    }
}
