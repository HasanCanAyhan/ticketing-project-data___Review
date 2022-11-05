package com.cydeo.entity;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //we added one more field : isDeleted for delete function, we will not delete user-data from DB
    private boolean isDeleted = false;


    @Column(nullable = false,updatable = false) //means: this field can not be null and if you click on the update button , this field will not change
    private LocalDateTime insertDateTime;

    @Column(nullable = false,updatable = false)
    private Long insertUserId; // who login in , related to security

    @Column(nullable = false,updatable = true)
    private LocalDateTime lastUpdateDateTime;
    @Column(nullable = false,updatable = true)
    private Long lastUpdateUserId;


    @PrePersist //means save
    private void onPrePersist(){ // will executed whenever we create new object (user) from UI-Part
        this.insertDateTime = LocalDateTime.now();
        this.insertUserId = 1L;
        this.lastUpdateDateTime = LocalDateTime.now();
        this.lastUpdateUserId=1L;


    }

    @PreUpdate
    private void onPreUpdate(){ //will executed whenever we update the object(ex:user)
        this.lastUpdateDateTime = LocalDateTime.now();
        this.lastUpdateUserId=1L;

    }






}
