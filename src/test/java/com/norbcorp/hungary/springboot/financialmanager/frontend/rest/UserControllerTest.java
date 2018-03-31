package com.norbcorp.hungary.springboot.financialmanager.frontend.rest;

import com.norbcorp.hungary.springboot.financialmanager.Application;
import com.norbcorp.hungary.springboot.financialmanager.backend.model.User;

import com.norbcorp.hungary.springboot.financialmanager.rest.MainRestController;
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
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=Application.class)
@WebMvcTest(MainRestController.class)
public class UserControllerTest {
    private static Logger logger = Logger.getAnonymousLogger();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MainRestController service;

    private RequestPostProcessor testUser() {
        return user("user").password("password").roles("USER");
    }

    @Test
    public void testGetALL() throws Exception {
        logger.info("Tests of rest api");
        User user = new User();
        user.setName("test1");

        List<User> allUsers = Arrays.asList(user);

        given(service.getAll()).willReturn(allUsers);

        // logger.info("*** MVC perform string object"+mvc.perform(get("/api/user/all").with(testUser())).andReturn().getResponse().getContentAsString());

        mvc.perform(get("/api/user/all")
                .contentType(MediaType.APPLICATION_JSON).with(testUser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(user.getName()));
    }
}

