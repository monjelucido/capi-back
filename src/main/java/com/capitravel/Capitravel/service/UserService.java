package com.capitravel.Capitravel.service;

import com.capitravel.Capitravel.dto.UserDTO;
import com.capitravel.Capitravel.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> getAllUsers();

    User saveUser(UserDTO user);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);

    Set<Long> toggleFavorite(String username, Long experienceId);

    List<Long> listFavorites(String username);
}
