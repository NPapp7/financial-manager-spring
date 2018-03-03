package com.norbcorp.hungary.springboot.demo.backend;

import com.norbcorp.hungary.springboot.demo.backend.model.Expenses;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesRepository extends CrudRepository<Expenses,Long> {
}
