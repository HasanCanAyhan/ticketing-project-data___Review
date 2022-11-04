package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> listAllProjects();

    void save(ProjectDTO project);

    ProjectDTO findByProjectCode(String projectCode);

}
