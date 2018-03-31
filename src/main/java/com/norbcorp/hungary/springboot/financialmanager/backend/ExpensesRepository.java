package com.norbcorp.hungary.springboot.financialmanager.backend;

import com.norbcorp.hungary.springboot.financialmanager.backend.model.Expenses;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesRepository extends CrudRepository<Expenses,Long> {
}
