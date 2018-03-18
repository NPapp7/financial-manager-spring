package com.norbcorp.hungary.springboot.demo.rest;

import com.norbcorp.hungary.springboot.demo.backend.TaskRepository;
import com.norbcorp.hungary.springboot.demo.backend.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}
