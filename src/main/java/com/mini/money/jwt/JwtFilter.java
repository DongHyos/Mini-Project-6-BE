package com.mini.money.jwt;



import com.mini.money.dto.member.LoginRequest;
import com.mini.money.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Builder
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    //시큐리티 필터 전에 유저 권한이나 인증 관련 정보를 넘겨주는 클래스

    private final JwtProvider jwtProvider;
    private final TokenService tokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION); //상수형태

        try{
            if(!tokenService.checkBlacklist(tokenHeader)){
                LoginRequest loginRequest = jwtProvider.tokenToUser(tokenHeader);
                if(loginRequest !=null) {
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                            loginRequest,
                            "",
                            loginRequest.getAuthorities()));
                }
            }

        }catch (ExpiredJwtException exception) {
            logger.error("ExpiredJwtException : expired token");
        } catch (Exception exception) {
            logger.error("Exception : no token");
        }

        filterChain.doFilter(request,response);
    }

}
