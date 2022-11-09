package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.Task;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    private  final ProjectMapper projectMapper;

    private final UserService userService;

    private final UserMapper userMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, ProjectMapper projectMapper, UserService userService, UserMapper userMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @Override
    public List<TaskDTO> listAllTasks() {

        List<Task> taskList = taskRepository.findAll();

        return taskList.stream()
                .map(task -> taskMapper.convertToDto(task))
                .collect(Collectors.toList());

    }

    @Override
    public void save(TaskDTO task) {
        task.setTaskStatus(Status.OPEN);
        task.setAssignedDate(LocalDate.now());
        Task task1 = taskMapper.convertToEntity(task);
        taskRepository.save(task1);

    }

    @Override
    public void deleteByTaskId(Long id) { //soft deleting only from UI-Part

        Optional<Task> task = taskRepository.findById(id);

        if(task.isPresent()){
            task.get().setIsDeleted(true);
            taskRepository.save(task.get());
        }


    }

    @Override
    public TaskDTO findById(Long taskId) { // for selecting the person who will be updated

        Task task = taskRepository.findById(taskId).orElseThrow();

        return taskMapper.convertToDto(task);

    }

    @Override
    public void update(TaskDTO taskDTO) { // taskDto has already id

        //find task from db
        Task task1 = taskRepository.findById(taskDTO.getId()).orElseThrow(); // has id

        //convert taskDto -> task entity
        Task convertedTask = taskMapper.convertToEntity(taskDTO);
        //set
        convertedTask.setAssignedDate(task1.getAssignedDate());

        //bug4
        //convertedTask.setTaskStatus(task1.getTaskStatus());
        convertedTask.setTaskStatus(taskDTO.getTaskStatus() == null ? task1.getTaskStatus() : taskDTO.getTaskStatus()  );
        //            if it is coming null from ui-part , get me this from DB            :   else if there is a status get me just task's status

        taskRepository.save(convertedTask);

    }

    @Override
    public int getCountsAllUnfinishedTasks(ProjectDTO projectDTO) {
        return taskRepository.fetchAllTasksUnfinished(projectDTO.getProjectCode());
    }

    @Override
    public int getCountsAllFinishedTasks(ProjectDTO projectDTO) {
        return taskRepository.fetchAllTasksCompleted(projectDTO.getProjectCode());
    }

    @Override
    public void deleteByProject(ProjectDTO projectDTO) { // for bug2 : delete all tasks related to the deleting project
        Project project = projectMapper.convertToEntity(projectDTO);
        List<Task> tasks = taskRepository.findAllByProject(project);
        tasks.forEach(task -> deleteByTaskId(task.getId()));


    }

    @Override
    public void completeByProject(ProjectDTO projectDTO) {
        Project project = projectMapper.convertToEntity(projectDTO);
        List<Task> tasks = taskRepository.findAllByProject(project);
        tasks.stream().map(task -> taskMapper.convertToDto(task)).forEach(taskDTO -> {

            taskDTO.setTaskStatus(Status.COMPLETE);
            update(taskDTO);

        });
    }

    @Override
    public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {
        // all tasks related to the employee log in the system
        UserDTO loggedInUser_employee = userService.findByUserName("john@employee.com");
        User employee = userMapper.convertToEntity(loggedInUser_employee);
        List<Task> tasks = taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(status,employee);

        return tasks.stream().map(task -> taskMapper.convertToDto(task)).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllTasksByStatus(Status status) {
        // all tasks related to the employee log in the system
        UserDTO loggedInUser_employee = userService.findByUserName("john@employee.com");
        User employee = userMapper.convertToEntity(loggedInUser_employee);

        List<Task> tasks = taskRepository.findAllByTaskStatusAndAssignedEmployee(status,employee);

        return tasks.stream().map(task -> taskMapper.convertToDto(task)).collect(Collectors.toList());

    }


}
