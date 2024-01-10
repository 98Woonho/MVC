package com.example.demo.domain.repository;

import com.example.demo.domain.entity.ImageBoardFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageBoardFileInfoRepository extends JpaRepository<ImageBoardFileInfo, Long> {

    List<ImageBoardFileInfo> findByImageBoardId(Long imageBoardId); // ImageBoardFileInfo 테이블의 ImageBoard 의 id를 가져옴.
}
