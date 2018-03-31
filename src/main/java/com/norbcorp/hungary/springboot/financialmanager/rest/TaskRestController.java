package com.norbcorp.hungary.springboot.financialmanager.rest;

import com.norbcorp.hungary.springboot.financialmanager.backend.TaskRepository;
import com.norbcorp.hungary.springboot.financialmanager.backend.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/task/")
public class TaskRestController {

    private static Logger logger = Logger.getLogger(TaskRestController.class.getName());

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody Iterable<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    @GetMapping(value="{taskId}")
    public @ResponseBody Task getTaskById(@PathVariable("taskId") Long taskId){
        return taskRepository.findOne(taskId);
    }

}
