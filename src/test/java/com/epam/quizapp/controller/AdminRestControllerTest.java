package com.epam.quizapp.controller;

import com.epam.quizapp.data.User;
import com.epam.quizapp.exception.QuestionMapWithQuizException;
import com.epam.quizapp.exception.UserException;
import com.epam.quizapp.models.AuthenticationRequest;
import com.epam.quizapp.service.AdminService;
import com.epam.quizapp.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class AdminRestControllerTest {
    User user;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    JwtUtil jwtUtil;
    @MockBean
    AdminService adminService;
    AuthenticationRequest authenticationRequest;
    @BeforeEach
    void setUp(){
        user = new User();
        user.setPassword("password");
        user.setActive(true);
        user.setUsername("username");
        user.setRole("USER");
        authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setPassword("password");
        authenticationRequest.setUsername("username");

    }
    @Test
    void addUser() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user));


        mockMvc.perform(mockRequest).andExpect(status().isCreated());

    }
    @Test
    void addUserError() throws Exception {
        doThrow(UserException.class).when(adminService).insert(any());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user));


        mockMvc.perform(mockRequest).andExpect(status().isBadRequest());

    }


    @Test
    void createAuthenticationToken() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/usersLog")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

                .content(this.mapper.writeValueAsString(authenticationRequest));


        mockMvc.perform(mockRequest).andExpect(status().isOk());

    }
    @Test
    void createAuthenticationTokenError() throws Exception {
        doThrow(BadCredentialsException.class).when(authenticationManager).authenticate(any());
        try {


            MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/usersLog")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)

                    .content(this.mapper.writeValueAsString(authenticationRequest));


            mockMvc.perform(mockRequest).andExpect(status().isOk());

        }catch (Exception e){
            System.out.println(e);
        }
    }
}