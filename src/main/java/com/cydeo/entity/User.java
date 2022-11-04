package com.cydeo.entity;

import com.cydeo.enums.Gender;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String firstName;
    private String lastName;

    private String userName;


    private String passWord;


    @Transient
    private boolean enabled;


    private String phone;


    @ManyToOne
    private Role role;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "assignedManager")
    private List<Project> projectList;


    public User(Long id, LocalDateTime insertDateTime, Long insertUserId, LocalDateTime lastUpdateDateTime, Long lastUpdateUserId, String firstName, String lastName, String userName, String passWord, boolean enabled, String phone, Role role, Gender gender) {
        super(id, insertDateTime, insertUserId, lastUpdateDateTime, lastUpdateUserId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.passWord = passWord;
        this.enabled = enabled;
        this.phone = phone;
        this.role = role;
        this.gender = gender;
    }





}
