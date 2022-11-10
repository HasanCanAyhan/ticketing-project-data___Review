package com.cydeo.entity;

import com.cydeo.enums.Gender;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")

//@Where(clause = "is_deleted=false") // put it to all repository which work with user entity, as query "where isDeleted = false;"
//we will delete from UI-Part , not from DB
//We only want to get the ones that is not deleted,so it means is_deleted needs to be false.
public class User extends BaseEntity {


    private String firstName;
    private String lastName;

    private String userName;


    private String passWord;





    private boolean enabled;


    private String phone;


    @ManyToOne
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;





}
