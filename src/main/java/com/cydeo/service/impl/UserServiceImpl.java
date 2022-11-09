package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
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

    private final ProjectService projectService;

    private final TaskService taskService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, ProjectService projectService, TaskService taskService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.taskService = taskService;
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

        return findByUserName(user.getUserName()); //method return UserDto,  you can also return like this  findByUserName(convertedUser.getUserName());

    }

    @Override
    public List<UserDTO> findAllByRoleId(Long roleId) { // to find managers and employees
        List<User> users = userRepository.findAllByRole_Id(roleId);

        List<UserDTO> userDTOList = users.stream()
                .map(user -> userMapper.convertToDto(user))
                .collect(Collectors.toList());


        return userDTOList;
    }


    @Override
    public void delete(String username) {// delete method will delete user only from UI-Part, nor from DB

        //find user from db with userName
        //change the isDeleted field to true, so that we can see if this data from UI-Part is deleted or not
        //save the object in the db

        User user_entity = userRepository.findByUserName(username);

        if (checkIfUserCanBeDeleted(user_entity)){
            user_entity.setDeleted(true);
            userRepository.save(user_entity);
        }



    }


    //Bug6 : if manager has some projects which is not complete, we should not delete him from the userList -ui part
    //       if employee has some tasks which is not complete, we should not delete him from the userList -ui part

    private boolean checkIfUserCanBeDeleted(User user){ // bcs method is private,we use this method just inside this class,  you can pass user enitity or userDto


        switch (user.getRole().getDescription()){

            case "Manager":
               List<ProjectDTO> projectDTOList = projectService.listAllNonCompletedByAssignedManager(userMapper.convertToDto(user));
               return projectDTOList.size() == 0;//we can delete manager

            case "Employee":
                List<TaskDTO> taskDTOList = taskService.listAllNonCompletedByAssignedEmployee(userMapper.convertToDto(user));
                return taskDTOList.size() == 0;//we can delete employee

            default:
                return true;

        }




    }

}
