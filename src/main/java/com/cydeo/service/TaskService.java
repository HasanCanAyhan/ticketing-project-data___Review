package com.cydeo.service;

import com.cydeo.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    List<TaskDTO> listAllTasks();

    void save(TaskDTO task);

    void deleteByTaskId(Long id);

    TaskDTO findById(Long taskId);

    void update(TaskDTO task);
}
