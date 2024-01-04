package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity // 이 클래스가 DB에 있는 테이블을 의미함.
public class ImageBoardFileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // = Auto increment
    private Long id;

    @ManyToOne
    @JoinColumn(name = "iid", foreignKey = @ForeignKey(name = "FK_imageFileInfo_imageBoard", foreignKeyDefinition = "FOREIGN KEY(iid) REFERENCES image_board(id) ON DELETE CASCADE ON UPDATE CASCADE")) // 외래키 지정
    private ImageBoard imageBoard;
    private String dir;
    private String filename;
}
