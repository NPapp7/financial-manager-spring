package com.norbcorp.hungary.springboot.financialmanager.tests.integration.rest;

import com.norbcorp.hungary.springboot.financialmanager.Application;
import com.norbcorp.hungary.springboot.financialmanager.backend.model.Task;
import com.norbcorp.hungary.springboot.financialmanager.rest.TaskRestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=Application.class)
@WebMvcTest(TaskRestController.class)
public class TaskControllerTest {
    private static Logger logger = Logger.getLogger(TaskControllerTest.class.getName());

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TaskRestController service;

    private RequestPostProcessor testUser() {
        return user("testuser").password("password").roles("USER");
    }

    @Test
    public void testGetAll() throws Exception {
        Task task = new Task();
        task.setTaskName("test task 1");

        List<Task> allTasks = Arrays.asList(task);

        given(service.getAllTasks()).willReturn(allTasks);

        mvc.perform(get("/api/task/all")
                .contentType(MediaType.APPLICATION_JSON).with(testUser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].taskName").value(task.getTaskName()));
    }

    @Test
    public void testDelete() throws Exception{
        Task task = new Task();
        task.setId(1);
        task.setTaskName("test task 1");

        Task task2 = new Task();
        task2.setId(2);
        task2.setTaskName("test task 2");

        List<Task> allTasks = Arrays.asList(task, task2);

        given(service.getAllTasks()).willReturn(allTasks);

        mvc.perform(get("/api/task/all")
                .contentType(MediaType.APPLICATION_JSON).with(testUser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].taskName").value(task.getTaskName()))
                .andExpect(jsonPath("$[1].taskName").value(task2.getTaskName()));

        mvc.perform(delete("/api/task/delete/{id}",task.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8).with(testUser()))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetTaskById() throws Exception{
        Task task = new Task();
        task.setId(1);
        task.setTaskName("test task 1");

        List<Task> allTasks = Arrays.asList(task);

        given(service.getTaskById(1l)).willReturn(task);

        mvc.perform(get("/api/task/{taskId}",1)
                .contentType(MediaType.APPLICATION_JSON).with(testUser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['id']").value(task.getId()))
                .andExpect(jsonPath("$['taskName']").value(task.getTaskName()));
    }
}
