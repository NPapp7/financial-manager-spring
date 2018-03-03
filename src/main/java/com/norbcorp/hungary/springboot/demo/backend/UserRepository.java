package com.norbcorp.hungary.springboot.demo.backend;

import com.norbcorp.hungary.springboot.demo.backend.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
