package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User,Long> {
    //Repository -> DB

    //get user based on username
    User findByUserName(String username);

    @Transactional
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


}
