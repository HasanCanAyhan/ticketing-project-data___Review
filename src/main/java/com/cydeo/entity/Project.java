package com.cydeo.entity;


import com.cydeo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "projects")
public class Project extends BaseEntity{

    private String projectName;
    private String projectCode;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User assignedManager;

    private LocalDate startDate;
    private LocalDate endDate;
    private String projectDetail;

    @Enumerated(EnumType.STRING)
    private Status projectStatus;
    private int completeTaskCounts;
    private int unfinishedTaskCounts;


}
