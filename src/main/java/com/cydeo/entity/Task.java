package com.cydeo.entity;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
@Where(clause = "is_deleted=false")
public class Task extends BaseEntity{


    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    /*Error : bcs projectDto did not have id
    TransientPropertyValueException: object references an unsaved transient instance -
    save the transient instance before flushing :
    com.cydeo.entity.Task.project -> com.cydeo.entity.Project
    nested exception is java.lang.IllegalStateException: org.hibernate.TransientPropertyValueException:
    object references an unsaved transient instance - save the transient instance before flushing :
    com.cydeo.entity.Task.project -> com.cydeo.entity.Project
     */



    @ManyToOne
    @JoinColumn(name = "assigned_employee_id")
    private User assignedEmployee;


    private Boolean isDeleted = false;


    private String taskSubject;

    private String taskDetail;

    @Enumerated(EnumType.STRING)
    private Status taskStatus;

    private LocalDate assignedDate;

}
