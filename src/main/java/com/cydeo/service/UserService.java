package com.cydeo.service;

import com.cydeo.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserService {

    //UI->Controller->Service

    List<UserDTO> listAllUsers();
    UserDTO findByUserName(String username);//for update button in the UI-Part
    void save(UserDTO user);
    void deleteByUserName(String username);

    UserDTO update(UserDTO user); // we return UserDto for security-part



}
