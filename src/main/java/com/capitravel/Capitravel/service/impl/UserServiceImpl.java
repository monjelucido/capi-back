package com.capitravel.Capitravel.service.impl;

import com.capitravel.Capitravel.dto.UserDTO;
import com.capitravel.Capitravel.exception.DuplicatedResourceException;
import com.capitravel.Capitravel.exception.ResourceNotFoundException;
import com.capitravel.Capitravel.model.Role;
import com.capitravel.Capitravel.model.User;
import com.capitravel.Capitravel.repository.ExperienceRepository;
import com.capitravel.Capitravel.repository.RoleRepository;
import com.capitravel.Capitravel.repository.UserRepository;
import com.capitravel.Capitravel.service.EmailService;
import com.capitravel.Capitravel.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    public static final String DEFAULT_ROLE = "ROLE_USER";
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new DuplicatedResourceException("The email: " + userDTO.getEmail() + " is already used");
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role role = roleRepository.findByName(DEFAULT_ROLE)
                .orElseThrow(() -> new ResourceNotFoundException("Role 'USER' not found"));
        user.setRole(role);

        User savedUser = userRepository.save(user);
        emailService.sendConfirmationEmail(savedUser.getEmail(), savedUser.getName(), savedUser.getLastName());
        return savedUser;
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleName));

        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    @Override
    public Set<Long> toggleFavorite(String username, Long experienceId) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        experienceRepository.findById(experienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with id: " + experienceId));

        if (user.getFavoriteExperienceIds().contains(experienceId)) {
            user.getFavoriteExperienceIds().remove(experienceId);
        } else {
            user.getFavoriteExperienceIds().add(experienceId);
        }

        userRepository.save(user);
        return user.getFavoriteExperienceIds();
    }

    @Override
    public List<Long> listFavorites(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new ArrayList<>(user.getFavoriteExperienceIds());
    }
}
