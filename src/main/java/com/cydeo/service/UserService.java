package com.cydeo.service;

import com.cydeo.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserService {

    //UI->Controller->Service

    List<UserDTO> listAllUsers();
    UserDTO findByUserName(String username);//for update button in the UI-Part
    void save(UserDTO user);
    void deleteByUserName(String username); // deletes everyting data from db

    UserDTO update(UserDTO user); // we return UserDto for security-part


    List<UserDTO> findManagers();

    void delete(String username);// it will delete or not show userdto in the UI-Part, still stay in db with isDeleted = true info
}
