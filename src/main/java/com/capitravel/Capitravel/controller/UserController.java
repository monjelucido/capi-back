package com.capitravel.Capitravel.controller;

import com.capitravel.Capitravel.dto.UserDTO;
import com.capitravel.Capitravel.exception.ForbiddenException;
import com.capitravel.Capitravel.model.User;
import com.capitravel.Capitravel.service.TokenValidationService;
import com.capitravel.Capitravel.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    TokenValidationService tokenValidationService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserDTO userDTO) {
        User newUser = userService.saveUser(userDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email, HttpServletRequest request) {
        tokenValidationService.authorize(request, email);
        User user = userService.getUser(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/role/{email}")
    public ResponseEntity<Void> addRoleToUser(@PathVariable String email, @RequestParam String roleName) {
        userService.addRoleToUser(email, roleName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/favorites/{email}")
    public ResponseEntity<Set<Long>> toggleFavorite(@PathVariable String email, @RequestParam Long experienceId, HttpServletRequest request) {
        tokenValidationService.authorize(request, email);
        Set<Long> favoritesUpdated = userService.toggleFavorite(email, experienceId);
        return new ResponseEntity<>(favoritesUpdated , HttpStatus.OK);
    }

    @GetMapping("/favorites/{email}")
    public List<Long> listFavorites(@PathVariable String email, HttpServletRequest request) {
        tokenValidationService.authorize(request, email);
        return userService.listFavorites(email);
    }
}
