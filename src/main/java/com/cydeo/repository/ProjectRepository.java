package com.cydeo.repository;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    Project findByProjectCode(String projectCode);


    List<Project> findAllByAssignedManager(User manager); // to find all projects belongs to the manager


    List<Project> findAllByProjectStatusIsNotAndAssignedManager(Status complete, User assignedManager); // for bug 6
}
