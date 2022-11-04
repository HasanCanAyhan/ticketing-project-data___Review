package com.cydeo.mapper;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper extends ModelMapper implements Mapper<Project, ProjectDTO> {
    @Override
    public Project convertToEntity(ProjectDTO dto) {
        return map(dto,Project.class);
    }

    @Override
    public ProjectDTO convertToDto(Project entity) {
        return map(entity,ProjectDTO.class);
    }
}
