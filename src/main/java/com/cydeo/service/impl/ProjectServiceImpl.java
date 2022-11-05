package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import org.springframework.data.domain.Sort;
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

        List<Project> projectList = projectRepository.findAll(Sort.by("projectCode"));

        List<ProjectDTO> projectDTOList = projectList.stream().map(project -> projectMapper.convertToDto(project)).collect(Collectors.toList());

        return projectDTOList;
    }

    @Override
    public void save(ProjectDTO projectDto) { // project is dto here

        projectDto.setProjectStatus(Status.OPEN);

        Project project = projectMapper.convertToEntity(projectDto);

        projectRepository.save(project);


    }

    @Override
    public void update(ProjectDTO projectDTO) {

        //find project from db
        Project project_entity = projectRepository.findByProjectCode(projectDTO.getProjectCode()); //has id

        //dto -->> entity
        Project convertedProject = projectMapper.convertToEntity(projectDTO); // has no id, it will be saved into db

        //before saving, set those fields
        convertedProject.setId(project_entity.getId());
        convertedProject.setProjectStatus(project_entity.getProjectStatus());

        projectRepository.save(convertedProject);

    }

    @Override
    public void delete(String code) { // soft deleting :we changed just field ,  we are not deleting data from db

        Project project = projectRepository.findByProjectCode(code);

        project.setDeleted(true);

        projectRepository.save(project);


    }

    @Override
    public ProjectDTO findByProjectCode(String projectCode) {

        Project project_entity = projectRepository.findByProjectCode(projectCode);

        return projectMapper.convertToDto(project_entity);

    }

    @Override
    public void complete(String projectCode) {

        Project project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE); // change the status and then save
        projectRepository.save(project);


    }

    @Override
    public List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager) {

        List<Project> projectList = projectRepository.findAllByAssignedManager(manager);



                List<ProjectDTO> projectDTOList = projectList.stream()
                .map(project -> projectMapper.convertToDto(project))
                .collect(Collectors.toList());

        return projectDTOList;

    }


}
