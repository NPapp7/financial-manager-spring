package com.norbcorp.hungary.springboot.financialmanager.frontend;

import com.norbcorp.hungary.springboot.financialmanager.backend.model.Balance;
import com.norbcorp.hungary.springboot.financialmanager.backend.model.Expenses;
import com.norbcorp.hungary.springboot.financialmanager.backend.ExpensesRepository;
import com.norbcorp.hungary.springboot.financialmanager.backend.TaskRepository;
import com.norbcorp.hungary.springboot.financialmanager.backend.model.TaskRelatedExpenses;
import com.norbcorp.hungary.springboot.financialmanager.util.CostCategory;
import com.norbcorp.hungary.springboot.financialmanager.util.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
public class HomeController {

    private static Logger logger = Logger.getLogger(HomeController.class.getName());

    @Inject
    private Balance balance;

    @Autowired
    private ExpensesRepository expensesRepository;
    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String users(Map<String,Object> model){
        return "redirect:/home";
    }

    @RequestMapping(value="/home", method = RequestMethod.GET)
    public String getUsersForHome(Map<String,Object> model){
        List<TaskRelatedExpenses> expenses = new LinkedList<>();
        long sum = 0;
        for(Iterator<Expenses> iterator = expensesRepository.findAll().iterator();iterator.hasNext();){
            Expenses expense = iterator.next();
            sum+=expense.getCost();
            TaskRelatedExpenses taskRelatedExpenses;
            if(taskRepository.findOne(expense.getRelatedTaskId())!=null)
                taskRelatedExpenses = new TaskRelatedExpenses(expense, taskRepository.findOne(expense.getRelatedTaskId()).toString());
            else
                taskRelatedExpenses = new TaskRelatedExpenses(expense, "");
            expenses.add(taskRelatedExpenses);
        }
        model.put("tasks", taskRepository.findAll());
        model.put("expenses", expenses);
        model.put("sum", sum);
        model.put("categories", CostCategory.values());
        model.put("itemNumber", expenses.size());
        model.put("typeOfTransaction", TransactionType.values());
        model.put("balance", balance.getBalance()-sum);
        return "home";
    }

    @RequestMapping(value="/home/deleteTransaction/{id}", method=RequestMethod.POST)
    public String deleteTransaction(@PathVariable Long id){
        expensesRepository.delete(id);
        return "redirect:/home";
    }

    @RequestMapping(value="/home/changeBalance", method=RequestMethod.POST)
    public String changeBalance(Integer balance){
        this.balance.setBalance(balance);
        return "redirect:/home";
    }

    @RequestMapping(value="/home",method = RequestMethod.POST)
    public String addExpenseToHome(Expenses expense){
        if(expense!=null && expense.getProduct()!=null && expense.getCategory()!=null && expense.getCost()!=null)
            expensesRepository.save(expense);
        return "redirect:/home";
    }
}
