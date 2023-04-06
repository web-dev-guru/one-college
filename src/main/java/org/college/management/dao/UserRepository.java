package org.college.management.dao;

import org.college.management.dao.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    User save(User user);
    @Query(value = "{ 'email': ?0 }")
    List<User> findUserByEmail(String email);

    List<User> findAll();

    @Query(value = "{ 'firstName': ?0 }")
    List<User> findUserByFirstName(String firstName);

    @Query(value = "{ 'lastName': ?0 }")
    List<User> findUserByLastName(String lastName);


    @Query(value = "{ 'lastName': ?0, 'firstName': ?1 }")
    List<User> findUserByLastNameFirstName(String lastName,String firstName);


}
