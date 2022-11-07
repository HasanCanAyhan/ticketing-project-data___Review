package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    private final UserService userService;
    private final UserMapper userMapper;

    private final TaskService taskService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserService userService, UserMapper userMapper, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.taskService = taskService;
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
    public List<ProjectDTO> listAllProjectDetails() {

        //this way is by not using repository , only service
        /* this way is by not using repository , only service

        // first find the manager
        UserDTO managerDTO = userService.findByUserName("harold@manager.com");

        List<ProjectDTO> projectDTOList = listAllProjects().stream()
                .filter(projectDTO -> projectDTO.getAssignedManager().getUserName().equals(managerDTO.getUserName())).collect(Collectors.toList());

        //completedtask and uncompleted tasks

        List<ProjectDTO> projectDTOS_with2Fields = projectDTOList.stream().map(projectDTO -> {

            int a =  (int) taskService.listAllTasks().stream()
                    .filter(taskDTO -> taskDTO.getProject().getProjectCode().equals(projectDTO.getProjectCode()))
                    .filter(taskDTO -> taskDTO.getTaskStatus() != Status.COMPLETE).count();

            projectDTO.setUnfinishedTaskCounts(a);

            int b = (int) taskService.listAllTasks().stream()
                    .filter(taskDTO -> taskDTO.getProject().getProjectCode().equals(projectDTO.getProjectCode()))
                    .filter(taskDTO -> taskDTO.getTaskStatus() == Status.COMPLETE).count();

            projectDTO.setCompleteTaskCounts(b);

            return projectDTO;

        }).collect(Collectors.toList());


        return projectDTOS_with2Fields;

         */


        //This way is by using repository

        // first find the manager who log in
        UserDTO currentManagerDTO = userService.findByUserName("harold@manager.com");//Manager will come from Security Topic
        User user = userMapper.convertToEntity(currentManagerDTO);//find the user from DB

        //go to DB, give me all the projects assigned to manager login in the system
        List<Project> projectList = projectRepository.findAllByAssignedManager(user);//all projects belongs to that manager(user)


        List<ProjectDTO> projectDTOList = projectList.stream().map(project -> projectMapper.convertToDto(project)).collect(Collectors.toList());

        return projectDTOList.stream().map(project -> {

            int countsAllUnfinishedTasks = taskService.getCountsAllUnfinishedTasks(project);
            int countsAllFinishedTasks = taskService.getCountsAllFinishedTasks(project);

            project.setUnfinishedTaskCounts(countsAllUnfinishedTasks);
            project.setCompleteTaskCounts(countsAllFinishedTasks);

            return project;

        }).collect(Collectors.toList());


    }

    //Harold is logginin in the system
    //Harold is able to see projects assigned to himself with finished and unfinished count tasks for each project
    //  3/1   open ,   2/2 InProgress








}
