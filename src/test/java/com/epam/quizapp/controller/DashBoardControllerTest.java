package com.epam.quizapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.epam.quizapp.service.AdminService;
import com.epam.quizapp.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DashBoardController.class)
class DashBoardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtUtil jwtUtil;
    @MockBean
    AdminService adminService;
    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void dashBoard() throws Exception {

        mockMvc.perform(get("/dashBoard")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void createQuestion() throws Exception {

        mockMvc.perform(get("/createQuestion")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void question() throws Exception {

        mockMvc.perform(get("/question")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void quiz() throws Exception {

        mockMvc.perform(get("/quiz")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void createQuiz() throws Exception {

        mockMvc.perform(get("/createQuiz")).andExpect(status().isOk());
    }


}
