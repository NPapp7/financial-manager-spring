package com.norbcorp.hungary.springboot.financialmanager.rest;

import com.norbcorp.hungary.springboot.financialmanager.backend.TaskRepository;
import com.norbcorp.hungary.springboot.financialmanager.backend.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/task/")
public class TaskRestController {

    private static Logger logger = Logger.getLogger(TaskRestController.class.getName());

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping(value = "{taskId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody Task getTaskById(@PathVariable("taskId") Long taskId) {
        return taskRepository.findOne(taskId);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String delete(@PathVariable("id") Long taskId) {
        Task task = taskRepository.findOne(taskId);
        try {
            taskRepository.delete(task);
            return "[{\"status\": Successful}]";
        } catch (Exception e) {
            return "[{\"status\": Unsuccessful}, \"reason\": " + e.getMessage() + "}]";
        }
    }

    @RequestMapping(value = "add/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String addTask(@RequestBody Task task) throws Exception {
        try {
            taskRepository.save(task);
            return "[{\"status\": Successful}]";
        } catch (Exception e) {
            return "[{\"status\": Unsuccessful}, \"reason\": " + e.getMessage() + "}]";
        }
    }

}
