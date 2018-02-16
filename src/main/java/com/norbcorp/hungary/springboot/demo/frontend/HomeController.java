package com.norbcorp.hungary.springboot.demo.frontend;

import com.norbcorp.hungary.springboot.demo.backend.Expenses;
import com.norbcorp.hungary.springboot.demo.backend.ExpensesRepository;
import com.norbcorp.hungary.springboot.demo.util.CostCategory;
import com.norbcorp.hungary.springboot.demo.util.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.sql.rowset.spi.TransactionalWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
public class HomeController {

    private static Logger logger = Logger.getLogger(HomeController.class.getName());

    @Autowired
    private ExpensesRepository expensesRepository;

   @RequestMapping(value="/", method = RequestMethod.GET)
    public String users(Map<String,Object> model){
         return "redirect:/home";
    }

    @RequestMapping(value="/home", method = RequestMethod.GET)
    public String getUsersForHome(Map<String,Object> model){
        List<Expenses> expenses = new LinkedList<>();
        long sum = 0;
        for(Iterator<Expenses> iterator = expensesRepository.findAll().iterator();iterator.hasNext();){
            Expenses expense = iterator.next();
            sum+=expense.getCost();
            expenses.add(expense);
        }
        model.put("expenses", expenses);
        model.put("sum", sum);
        model.put("categories", CostCategory.values());
        model.put("itemNumber", expenses.size());
        model.put("typeOfTransaction", TransactionType.values());
        return "home";
    }

    @RequestMapping(value="/home/deleteTransaction/{id}", method=RequestMethod.POST)
    public String deleteTransaction(@PathVariable Long id){
       expensesRepository.delete(id);
       return "redirect:/home";
    };

    @RequestMapping(value="/home",method = RequestMethod.POST)
    public String addExpenseToHome(Expenses expense){
        if(expense!=null && expense.getProduct()!=null && expense.getCategory()!=null && expense.getCost()!=null)
            expensesRepository.save(expense);
        return "redirect:/";
    }

}
