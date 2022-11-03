package com.cydeo.converter;

import com.cydeo.dto.RoleDTO;

import com.cydeo.service.RoleService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class RoleDtoConverter implements Converter<String, RoleDTO> {


    RoleService roleService;

    public RoleDtoConverter(@Lazy RoleService roleService) { // to break circle (error)
        //                  inject , if  needed
        // Because  we dont use roledto converter until in the UI-Part user click on the save button
        //After save button, we need it, then inject roleService into roledtoconverter
        this.roleService = roleService;
    }

    @Override
    public RoleDTO convert(String source) { // ex : th :value is like "1":Admin , "2":Manager etc...we should return RoleDto object

        if (source == null || source.equals("")) {  //  Select  -> ""
            return null;
        }

        return roleService.findById(Long.parseLong(source)); // first, implementation was null, then we made impl

    }





}
