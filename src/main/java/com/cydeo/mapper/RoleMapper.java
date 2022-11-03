package com.cydeo.mapper;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class RoleMapper {

    private final ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Role convertToEntity(RoleDTO dto){ // for database

        return modelMapper.map(dto,Role.class);

    }


    public RoleDTO convertToDto(Role entity){ // for ui-part

        return modelMapper.map(entity,RoleDTO.class);

    }


}
