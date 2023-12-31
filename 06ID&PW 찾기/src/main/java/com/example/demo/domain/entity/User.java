package com.example.demo.domain.entity;

import com.example.demo.domain.dto.UserDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity // 이 클래스가 DB에 있는 테이블을 의미함.
@Table(name="user")
public class User {
    @Id
    private String username;
    private String password;
    private String role;
    private String nickname;
    private String zipcode;
    private String addr1;
    private String addr2;
    private String phone;

    //OAUTH2
    private String provider;
    private String providerId;

    public static UserDto entityToDto(User user){
        UserDto dto = UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .nickname(user.getNickname())
                .zipcode(user.getZipcode())
                .addr1(user.getAddr1())
                .addr2(user.getAddr2())
                .phone(user.getPhone())
                .provider(user.getProvider())
                .providerId(user.getProviderId())
                .build();
        return dto;
    }

}
