package com.norbcorp.hungary.springboot.financialmanager.frontend.rest;

import com.norbcorp.hungary.springboot.financialmanager.Application;
import com.norbcorp.hungary.springboot.financialmanager.backend.model.Task;
import com.norbcorp.hungary.springboot.financialmanager.backend.model.User;
import com.norbcorp.hungary.springboot.financialmanager.rest.MainRestController;
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
        return user("user").password("password").roles("USER");
    }

    @Test
    public void testGetAll() throws Exception {
        Task task = new Task();
        task.setTaskName("test task 1");

        List<Task> allTasks = Arrays.asList(task);

        given(service.getAllTasks()).willReturn(allTasks);

        // logger.info("*** MVC perform string object"+mvc.perform(get("/api/user/all").with(testUser())).andReturn().getResponse().getContentAsString());

        mvc.perform(get("/api/task/all")
                .contentType(MediaType.APPLICATION_JSON).with(testUser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(task.getTaskName()));
    }
}
