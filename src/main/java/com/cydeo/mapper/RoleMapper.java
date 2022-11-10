package com.cydeo.mapper;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class RoleMapper extends ModelMapper implements Mapper<Role,RoleDTO> {
    @Override
    public Role convertToEntity(RoleDTO dto) {
        return map(dto,Role.class);
    }

    @Override
    public RoleDTO convertToDto(Role entity) {
        return map(entity,RoleDTO.class);
    }


    //    /* First we used it , but then it is better to use ,
    //    first create Mapper Interface as Generic,
    //    classes will extend ModelMapper implement Mapper Interface




}
