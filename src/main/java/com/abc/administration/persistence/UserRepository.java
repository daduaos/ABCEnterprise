package com.abc.administration.persistence;

import org.springframework.data.repository.CrudRepository;

import com.abc.administration.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
