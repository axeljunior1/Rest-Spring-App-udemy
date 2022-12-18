package com.in28minutes.rest.webservices.restfulwebservices.repository;

import com.in28minutes.rest.webservices.restfulwebservices.Beans.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends  CrudRepository<User,Long>  {
}
