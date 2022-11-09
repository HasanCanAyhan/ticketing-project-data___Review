package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;

import java.util.List;

public interface TaskService {
    List<TaskDTO> listAllTasks();

    void save(TaskDTO task);

    void deleteByTaskId(Long id);

    TaskDTO findById(Long taskId);

    void update(TaskDTO task);


    int getCountsAllUnfinishedTasks(ProjectDTO projectDTO);

    int getCountsAllFinishedTasks(ProjectDTO projectDTO);

    void deleteByProject(ProjectDTO projectDTO); // for bug2

    void completeByProject(ProjectDTO projectDTO);// for bug3

    List<TaskDTO> listAllTasksByStatusIsNot(Status status); // for pending tasks related to employee who login the system
    List<TaskDTO> listAllTasksByStatus(Status status);// for archive tasks related to employee who login the system

    List<TaskDTO> listAllNonCompletedByAssignedEmployee(UserDTO assignedEmployee); // for bug 6
}
