package com.cydeo.repository;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    Project findByProjectCode(String projectCode);

}
