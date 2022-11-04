package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    //Service(Interface) -->> ServiceImpl(Class) ->>> Repository -->> DB

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public List<UserDTO> listAllUsers() {

        //serviceImpl ->>> Repository --->> DataBase
        //Service Impl return DTO
        //Repository return Entity

        List<User> userList = userRepository.findAll(Sort.by("firstName")); // go to DB and brings the users : entity

        // we should return DTO in the ServiceImpl, then it will go to controller for UI -Part

        return userList.stream()
                .map(user -> userMapper.convertToDto(user)).collect(Collectors.toList());


    }

    @Override
    public UserDTO findByUserName(String username) { // update button ,we click on update button, give me selected user from DB

        User user = userRepository.findByUserName(username);

        return userMapper.convertToDto(user);
    }

    @Override
    public void save(UserDTO user) {

        // save User(dto) which is coming from UI-Part in to DB as entity

        User user1 = userMapper.convertToEntity(user); // converted to entity

        userRepository.save(user1);

        //After running app, then give userdata intoUi, then give me error:
        //Property or field 'description' for role cannot be found on null : go to Role converter

    }

    @Override
    public void deleteByUserName(String username) { // delete button

        //first find the current user with userName from Database, then delete it from DB by using id
        //User currentUser = userRepository.findByUserName(username);
        //userRepository.deleteById(currentUser.getId());

        //or : you can create new method into UserRepository whose function is deleting, by using username
        userRepository.deleteByUserName(username);




    }

    @Override
    public UserDTO update(UserDTO user) { // this updated user is coming from UI-Part
        //ServiceImpl -> Repository-> DB
        //return dto to view in the controller

        //if we do like this: like save method
        //userRepository.save(userMapper.convertToEntity(user)); it will create another user with another primary key


        //first find the current user from DB
        User user1 = userRepository.findByUserName(user.getUserName()); //has id,  entity from DB

        //first convert user dto to user to save again into DB , then set id

        User convertedUser = userMapper.convertToEntity(user); // has no id ,now
        convertedUser.setId(user1.getId());

        //save

        userRepository.save(convertedUser);

        return findByUserName(user.getUserName()); // you can also return like this userRepository.save(convertedUser);

    }
}
