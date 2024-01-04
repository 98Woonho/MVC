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
public class ImageBoard {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String seller;
    private String productname;
    private String category;
    private String brandname;
    private String price;
    private String itemdetails;
    private String amount;
    private String size;
    private LocalDateTime createAt; // 등록 날짜

//  @ElementCollection // id열과 Annotation된 열을 FK로 하는 열로 구성된 테이블이 생성 됨.

    private List<String> files;
}
