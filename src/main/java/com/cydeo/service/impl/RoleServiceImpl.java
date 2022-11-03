package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.mapper.RoleMapper;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    //Service(Interface) -->> ServiceImpl(Class) ->>> Repository -->> DB

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }


    @Override
    public List<RoleDTO> listAllRoles() {

        //Controller called me and requesting all RoleDTOs , so it can show in the drop-down in the UI
        //I need to make a call to DB and get all the roles from table
        //Go to repository and find a service(method) which gives me the roles from db
        //how i will call any service here


        List<Role> roleList = roleRepository.findAll();

        return roleList.stream()
                .map(role -> roleMapper.convertToDto(role)).collect(Collectors.toList());

        //return roleList;
        // expectingRoleDTO; convert role to roleDto: we added modelmapper dependency
        // mapper class is not our class , so we will use @Bean annotation


    }

    @Override
    public RoleDTO findById(Long id) {
        return null;
    }
}
