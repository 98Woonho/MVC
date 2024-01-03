package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity // 이 클래스가 DB에 있는 테이블을 의미함.
@Table(name="ImageBoard")
public class ImageBoard {
    @GeneratedValue(strategy = GenerationType.IDENTITY) // = Auto increment
    @Id
    private Long id;
    private String seller;
    private String productname;
    private String category;
    private String brandname;
    private String itemdetails;
    private String amount;
    private String size;
    private LocalDateTime createAt; // 등록 날짜
    @ElementCollection // files라는 열이 있으면, files를 FK로 하는 테이블이 생성 됨.
    private List<String> files;
}
