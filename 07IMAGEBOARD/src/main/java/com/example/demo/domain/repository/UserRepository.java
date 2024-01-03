package com.example.demo.domain.repository;


import com.example.demo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository // Entity에 의해 생성된 DB에 접근하는 메서드들을 사용하기 위한 인터페이스이다.
// <User, String> : <엔티티, 엔티티의 id의 type>
public interface UserRepository extends JpaRepository<User,String> {
    User findByNicknameAndPhone(@Param(value = "nickname") String nickname, @Param(value = "phone") String phone);
}
