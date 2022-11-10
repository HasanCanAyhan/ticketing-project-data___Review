package com.cydeo.repository;

import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    //Repository -> DB


    List<User> findAllByIsDeletedOrderByFirstNameDesc(Boolean deleted);// for bug 9


    //get user based on username
    //User findByUserName(String username); //select * from users where user_name = 'username' and is_deleted = false;
    User findByUserNameAndIsDeleted(String username, Boolean deleted); // for bug 9


    @Transactional  //Transactional is used for derived query , @Modifing is used for JPQL and Native Query
    void deleteByUserName(String username); //derived query
    //we got the error:
    /*
   No EntityManager with actual transaction available for current thread
   - cannot reliably process 'remove' call; nested exception is javax.persistence.
   TransactionRequiredException: No EntityManager with actual transaction available for current thread
   - cannot reliably process 'remove' call
     */

    /*
    Transaction : is a defined set of mutable operations(operations that change data)
    These are essential in app bcs they ensure the data remains consistent if any step of the use case fails when the app already changed data

    We can start a transaction before step 1 and close the transaction after step2.
    If both steps successfully execute, when the transaction ends,
    we say : that transaction "commits"

    If step 1 executes without a problem , but step 2 fails any reason, the app reverts the changes step 1 made.
    This operation is named "rollback"
     */

    //List<User> findAllByRole_Id(Long id);

    List<User> findAllByRole_IdAndIsDeleted(Long id,Boolean deleted); // for bug 9



}
