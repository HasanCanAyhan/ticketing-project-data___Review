package com.cydeo.repository;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.Task;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {


    @Query("select count(t) from Task t where t.project.projectCode = ?1 and t.taskStatus <> 'COMPLETE'")
    int fetchAllTasksUnfinished(String projectCode);//jpql query

    @Query(value = "select count(*) from tasks t join projects p  on t.project_id = p.id  where project_code = ?1   and task_status = 'COMPLETE' ",nativeQuery = true)
    int fetchAllTasksCompleted(String projectCode); //native query


    List<Task> findAllByProject(Project project); //for bug2

    List<Task> findAllByTaskStatusIsNotAndAssignedEmployee(Status status, User loggedInUser_employee);

    List<Task> findAllByTaskStatusAndAssignedEmployee(Status status, User employee);
}
