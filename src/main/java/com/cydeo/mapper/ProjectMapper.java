package com.cydeo.mapper;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import org.modelmapper.ModelMapper;

public class ProjectMapper extends ModelMapper implements Mapper<Role, RoleDTO>{


    @Override
    public Role convertToEntity(RoleDTO dto) {
        return map(dto,Role.class);
    }

    @Override
    public RoleDTO convertToDto(Role entity) {
        return map(entity,RoleDTO.class);
    }
}
