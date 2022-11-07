package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> listAllProjects();

    void save(ProjectDTO projectDto);

    void update(ProjectDTO projectDTO);

    void delete(String code);

    ProjectDTO findByProjectCode(String projectCode);

    void complete(String projectCode);


    List<ProjectDTO> listAllProjectDetails();


}
