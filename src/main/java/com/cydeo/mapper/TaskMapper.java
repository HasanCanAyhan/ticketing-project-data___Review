package com.cydeo.mapper;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper extends ModelMapper implements Mapper<Task, TaskDTO> {
    @Override
    public Task convertToEntity(TaskDTO dto) {
        return map(dto,Task.class);
    }

    @Override
    public TaskDTO convertToDto(Task entity) {
        return map(entity,TaskDTO.class);
    }
}
