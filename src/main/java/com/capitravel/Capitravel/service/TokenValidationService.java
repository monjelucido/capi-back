package com.capitravel.Capitravel.service;

import jakarta.servlet.http.HttpServletRequest;

public interface TokenValidationService {
    void authorize(HttpServletRequest request, String email);
}
