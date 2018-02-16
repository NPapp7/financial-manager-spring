package com.norbcorp.hungary.springboot.demo.frontend;

import com.norbcorp.hungary.springboot.demo.backend.Task;
import com.norbcorp.hungary.springboot.demo.backend.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;
import java.util.logging.Logger;

@Controller
public class TaskController {
    private static Logger logger = Logger.getLogger(TaskController.class.getName());

    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping(value = "/task", method = RequestMethod.GET)
    public String getTaskPage(Map<String,Object> objectMap){
        objectMap.put("tasks",taskRepository.findAll());
        return "tasks";
    }

    @RequestMapping(value = "/task/deleteTask/{id}", method = RequestMethod.POST)
    public String deleteTask(@PathVariable Long id){
        taskRepository.delete(id);
        return "redirect:/task";
    }

    @RequestMapping(value="/task", method = RequestMethod.POST)
    public String addNewTask(Task task){
        if(task!=null && task.getDescription()!=null && !task.getDescription().isEmpty() && task.getDeadline()!=null && !task.getDeadline().isEmpty() && task.getTaskName()!=null && !task.getTaskName().isEmpty()) {
            taskRepository.save(task);
        } else
            logger.warning("ERROR - Not valid task!");
        return "redirect:/task";
    }
}
