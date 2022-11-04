package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }


    @Override
    public List<ProjectDTO> listAllProjects() {

        List<Project> projectList = projectRepository.findAll();

        List<ProjectDTO> projectDTOList = projectList.stream().map(project -> projectMapper.convertToDto(project)).collect(Collectors.toList());

        return projectDTOList;
    }

    @Override
    public void save(ProjectDTO project) { // project is dto here

        if (project.getProjectStatus() == null){

            project.setProjectStatus(Status.OPEN);

        }

        projectRepository.save( projectMapper.convertToEntity(project));


    }

    @Override
    public ProjectDTO findByProjectCode(String projectCode) {

        Project project_entity = projectRepository.findByProjectCode(projectCode);

        return projectMapper.convertToDto(project_entity);

    }


}
