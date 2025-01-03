package com.akashmailapalli.bike_spare_parts_store.service.serviceimpl;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.akashmailapalli.bike_spare_parts_store.dto.UserDTO;
import com.akashmailapalli.bike_spare_parts_store.model.Role;
import com.akashmailapalli.bike_spare_parts_store.model.User;
import com.akashmailapalli.bike_spare_parts_store.repository.UserRepository;
import com.akashmailapalli.bike_spare_parts_store.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("user not found"));
        userRepository.delete(user);
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        
        User appUser = modelMapper.map(userDTO, User.class);
        appUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        
        // Set default role if none is provided
        if (userDTO.getRole() == null) {
            appUser.setRole(Role.CUSTOMER);  // Default to ROLE_USER
        }

        // Save the user to the database
        User savedUser = userRepository.save(appUser);
        
        // Convert saved entity to DTO
        return modelMapper.map(savedUser, UserDTO.class);
     
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        // Find the user by ID
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update fields only if they are provided
        if (userDTO.getUsername() != null && !userDTO.getUsername().isEmpty()) {
            existingUser.setUsername(userDTO.getUsername());
        }
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        if (userDTO.getRole() != null) {
            existingUser.setRole(userDTO.getRole());
        }

        // Save updated user
        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

}
