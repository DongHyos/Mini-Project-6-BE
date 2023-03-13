package com.mini.money.dto.member;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Collections;

import static com.mini.money.dto.ValidatorMessage.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@ApiModel(value = "로그인")
public class LoginRequest {

    @NotBlank(message = EMPTY_MESSAGE)
    @Email(message = EMAIL_MESSAGE)
    private String email;

    @NotNull(message = EMPTY_MESSAGE)
    @Pattern(regexp = MEMBER_PW_FORMAT, message = MEMBER_PW_MESSAGE)
    private String password;

    public LoginRequest(Claims claims) {
        this.email = claims.get("email", String.class);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        // ROLE_USER, ROLE_ADMIN 은 정해저 있는 네이밍임, getAuthorities() 이름도 같아야함
        // 만약 하나로 구분이 아니고 여러 개의 권한을 가져야 한다면, 아래와 닽이 쓸것
        //return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"),new SimpleGrantedAuthority("ROLE_ADMIN"));
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
