package com.capitravel.Capitravel.service.impl;

import com.capitravel.Capitravel.exception.ForbiddenException;
import com.capitravel.Capitravel.security.JwtTokenUtil;
import com.capitravel.Capitravel.service.TokenValidationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenValidationServiceImpl implements TokenValidationService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public void authorize(HttpServletRequest request, String email) {
        String token = request.getHeader("Authorization").substring(7);
        String emailFromToken = jwtTokenUtil.getEmailFromToken(token);
        if (!emailFromToken.equals(email)) {
            throw new ForbiddenException();
        }
    }
}
