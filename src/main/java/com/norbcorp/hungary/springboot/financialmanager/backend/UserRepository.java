package com.norbcorp.hungary.springboot.financialmanager.backend;

import com.norbcorp.hungary.springboot.financialmanager.backend.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
