package com.cydeo.entity;


import com.cydeo.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "projects")
public class Project extends BaseEntity{

    private String projectName;
    private String projectCode;
    @ManyToOne(cascade = CascadeType.ALL)
    private User assignedManager;

    private LocalDate startDate;
    private LocalDate endDate;
    private String projectDetail;

    @Enumerated(EnumType.STRING)
    private Status projectStatus;
    private int completeTaskCounts;
    private int unfinishedTaskCounts;

    public Project(Long id, LocalDateTime insertDateTime, Long insertUserId, LocalDateTime lastUpdateDateTime, Long lastUpdateUserId, String projectName, String projectCode, User assignedManager, LocalDate startDate, LocalDate endDate, String projectDetail, Status projectStatus, int completeTaskCounts, int unfinishedTaskCounts) {
        super(id, insertDateTime, insertUserId, lastUpdateDateTime, lastUpdateUserId);
        this.projectName = projectName;
        this.projectCode = projectCode;
        this.assignedManager = assignedManager;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectDetail = projectDetail;
        this.projectStatus = projectStatus;
        this.completeTaskCounts = completeTaskCounts;
        this.unfinishedTaskCounts = unfinishedTaskCounts;
    }
}
