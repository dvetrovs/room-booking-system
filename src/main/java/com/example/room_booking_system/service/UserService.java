package com.example.room_booking_system.service;

import com.example.room_booking_system.dto.UserRequest;
import com.example.room_booking_system.dto.UserResponse;
import com.example.room_booking_system.entity.Role;
import com.example.room_booking_system.entity.User;
import com.example.room_booking_system.exception.ConflictException;
import com.example.room_booking_system.exception.ResourceNotFoundException;
import com.example.room_booking_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse createUser(UserRequest userRequest){
        String email = userRequest.email().trim().toLowerCase();
        if (userRepository.existsByEmail(email)){
            throw new ConflictException("Email already registered: " + email);
        }
        User user = new User();
        user.setName(userRequest.name().trim());
        user.setEmail(email);
        user.setRole(Role.USER);
        return UserResponse.from(userRepository.save(user));
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::from).toList();
    }

    public UserResponse getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return UserResponse.from(user);
    }
}
