package com.cydeo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class MapperUtil {

    private final ModelMapper modelMapper;


    public MapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /*
    //1.Implementation
    public <T> T convert(Object objToBeConverted,T convertedObject){
        return modelMapper.map(objToBeConverted, (Type) convertedObject.getClass());
    }


     */



    //2.Implementation
    public <T> T convert(Object objToBeConverted,Class<T> convertedObject){
        return modelMapper.map(objToBeConverted,convertedObject);
    }


}
