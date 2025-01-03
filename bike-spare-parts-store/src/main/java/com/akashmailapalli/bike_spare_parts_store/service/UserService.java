package com.akashmailapalli.bike_spare_parts_store.service;

import com.akashmailapalli.bike_spare_parts_store.dto.UserDTO;

public interface UserService {

    UserDTO registerUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
}
