package com.cydeo.mapper;

public interface Mapper<T,K> {

     T convertToEntity(K dto);

     K convertToDto(T entity);



}
