package com.mini.money.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private static final String MESSAGE = "특정 권한이 필요한 서비스입니다.";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        final Map<String, Object> body = new HashMap<>();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        body.put("status", HttpServletResponse.SC_FORBIDDEN);
        body.put("error", "Authentication");
        body.put("message", MESSAGE);
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        OutputStream outputStream = response.getOutputStream();
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        mapper.writeValue(outputStream, body);
        outputStream.flush();
    }
}
