package com.norbcorp.hungary.springboot.financialmanager.backend;

import com.norbcorp.hungary.springboot.financialmanager.backend.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>{
}
