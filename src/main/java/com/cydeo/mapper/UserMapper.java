package com.cydeo.mapper;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends ModelMapper implements Mapper<User,UserDTO>{
    @Override
    public User convertToEntity(UserDTO dto) {
        return map(dto,User.class);
    }

    @Override
    public UserDTO convertToDto(User entity) {
        return map(entity,UserDTO.class);
    }

    /*
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User convertToEntity(UserDTO dto){

        return modelMapper.map(dto,User.class);

    }



    public UserDTO convertToDto(User entity){

        return modelMapper.map(entity,UserDTO.class);

    }

     */

}
