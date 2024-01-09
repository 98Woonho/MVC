package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity // 이 클래스가 DB에 있는 테이블을 의미함.
public class Cart {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long cart_id;

    @ManyToOne
    @JoinColumn(name = "imageboard_id", foreignKey = @ForeignKey(name = "FK_Cart_imageBoard", foreignKeyDefinition = "FOREIGN KEY(imageboard_id) REFERENCES image_board(id) ON DELETE CASCADE ON UPDATE CASCADE")) // 외래키 지정
    private ImageBoard imageBoard;

    @ManyToOne
    @JoinColumn(name = "username", foreignKey = @ForeignKey(name = "FK_Cart_user_01", foreignKeyDefinition = "FOREIGN KEY(username) REFERENCES user(username) ON DELETE CASCADE ON UPDATE CASCADE")) // 외래키 지정
    private User user;

    @ManyToOne
    @JoinColumn(name = "imageboard_file_id", foreignKey = @ForeignKey(name = "FK_Cart_imageBoard_file", foreignKeyDefinition = "FOREIGN KEY(imageboard_file_id) REFERENCES image_board_file_info(id) ON DELETE CASCADE ON UPDATE CASCADE")) // 외래키 지정
    private ImageBoardFileInfo imageBoardFileInfo;

    private LocalDateTime regdate;

    private int amount;
    private String color;
}
