package com.epam.quizapp.controller;

import com.epam.quizapp.data.OptionDTO;
import com.epam.quizapp.data.QuestionDTO;
import com.epam.quizapp.exception.*;
import com.epam.quizapp.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuestionRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestionService questionService;
    QuestionDTO questionDTO;
    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        questionDTO = new QuestionDTO();

        questionDTO.setTitle("question1");
        questionDTO.setId(1);
        questionDTO.setDifficulty("easy");
        questionDTO.setTag("que");
        List<OptionDTO> optionsDTO = new ArrayList<>();
        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setAnswer(true);
        optionDTO.setValue("que1");
        optionsDTO.add(optionDTO);
        optionDTO.setAnswer(false);
        optionDTO.setValue("que2");
        optionsDTO.add(optionDTO);
        optionDTO.setAnswer(false);
        optionDTO.setValue("que2");
        optionsDTO.add(optionDTO);
        optionDTO.setAnswer(false);
        optionDTO.setValue("que2");
        optionsDTO.add(optionDTO);
        questionDTO.setOptions(optionsDTO);
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void getQuestions() throws Exception {

        QuestionDTO question1 = new QuestionDTO();
        QuestionDTO question2 = new QuestionDTO();
        QuestionDTO question3 = new QuestionDTO();
        List<QuestionDTO> questions = Arrays.asList(question1, question2, question3);

        when(questionService.getQuestion()).thenReturn(questions);
        mockMvc.perform(get("/questions"))
                .andExpect(status().isAccepted()).andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void addQuestion() throws Exception {

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

                .content(this.mapper.writeValueAsString(questionDTO));

        mockMvc.perform(mockRequest).andExpect(status().isCreated());

    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void addQuestionNotDone() throws Exception {
        doThrow(QuestionException.class).when(questionService).addQuestion(any());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

                .content(this.mapper.writeValueAsString(questionDTO));

        mockMvc.perform(mockRequest).andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void addQuestionValod() throws  Exception{
        questionDTO.setTitle("raj");
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

                .content(this.mapper.writeValueAsString(questionDTO));

        mockMvc.perform(mockRequest).andExpect(status().isBadRequest());

    }


    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void deleteQuestion() throws Exception {
        mockMvc.perform(delete("/questions/1")).andExpect(status().isNoContent());

    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void deleteQuestionNotFound() throws Exception {
        doThrow(QuestionNotFoundException.class).when(questionService).removeQuestion(any());
        mockMvc.perform(delete("/questions/1")).andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void deleteQuestionNotDone() throws Exception {
        doThrow(QuestionException.class).when(questionService).removeQuestion(any());
        mockMvc.perform(delete("/questions/1")).andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void deleteQuestionMapedWithQuiz() throws Exception {
        doThrow(QuestionMapWithQuizException.class).when(questionService).removeQuestion(any());
        mockMvc.perform(delete("/questions/1")).andExpect(status().isBadRequest());

    }


    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void editQuestion() throws Exception {
        when(questionService.find(1)).thenReturn(questionDTO);


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/questions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(questionDTO));

        mockMvc.perform(mockRequest).andExpect(status().isCreated());

    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void editQuestionNotDone() throws Exception {
        when(questionService.find(1)).thenReturn(questionDTO);

        doThrow(QuestionException.class).when(questionService).updateQuestion(any(),any());
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/questions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(questionDTO));

        mockMvc.perform(mockRequest).andExpect(status().isBadRequest());

    }

}