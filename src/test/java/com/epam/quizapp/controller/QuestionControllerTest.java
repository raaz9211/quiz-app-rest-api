package com.epam.quizapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.epam.quizapp.data.OptionDTO;
import com.epam.quizapp.data.QuestionDTO;
import com.epam.quizapp.exception.QuestionMapWithQuizException;
import com.epam.quizapp.service.AdminService;
import com.epam.quizapp.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.quizapp.data.Question;
import com.epam.quizapp.service.QuestionService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = QuestionController.class)
class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    QuestionService questionService;

    @MockBean
    AdminService adminService;
    @MockBean
    JwtUtil jwtUtil;




    private QuestionDTO questionDTO = new QuestionDTO();

    @BeforeEach
    void setUp() {
//
//		question1.setTitle("question1");
//		question1.setId(1);
//		question1.setDifficulty("easy");
//		question1.setTag("que");
//		List<Option> options = new ArrayList<>();
//		Option option = new Option();
//		option.setQuestion(question1);
//		option.setAnswer(true);
//		option.setValue("que1");
//		options.add(option);
//		option.setQuestion(question1);
//		option.setAnswer(false);
//		option.setValue("que2");
//		options.add(option);
//		option.setQuestion(question1);
//		option.setAnswer(false);
//		option.setValue("que2");
//		options.add(option);
//		option.setQuestion(question1);
//		option.setAnswer(false);
//		option.setValue("que2");
//		options.add(option);
//		question1.setOptions(options);
//
//		question2.setTitle("question2");
//		question2.setId(2);
//		question2.setDifficulty("easy");
//		question2.setTag("que");
//		question2.setOptions(options);


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
    void viewQuestion() throws Exception {
        QuestionDTO question1 = new QuestionDTO();
        QuestionDTO question2 = new QuestionDTO();
        QuestionDTO question3 = new QuestionDTO();
        List<QuestionDTO> questions = Arrays.asList(question1, question2, question3);

        when(questionService.getQuestion()).thenReturn(questions);
        mockMvc.perform(get("/viewQuestions")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void insertQuestion() throws Exception {
        when(questionService.find(1)).thenReturn(new QuestionDTO());
        mockMvc.perform(post("/insertQuestion").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("value", "value").param("isAnswer", "isAnswer").sessionAttr("question", new Question()))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void deleteQuestion() throws Exception {
        when(questionService.find(anyInt())).thenReturn(questionDTO);

        mockMvc.perform(get("/deleteQuestion?id=1")).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void deleteQuestion1() throws Exception {
        doThrow(QuestionMapWithQuizException.class).when(questionService).removeQuestion(any());

        mockMvc.perform(get("/deleteQuestion?id=1")).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void deleteQuestion2() throws Exception {
        doThrow(org.mockito.exceptions.base.MockitoException.class).when(questionService).removeQuestion(any());

        mockMvc.perform(get("/deleteQuestion?id=1")).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void editQuestion() throws Exception {
        when(questionService.find(anyInt())).thenReturn(questionDTO);
        mockMvc.perform(get("/editQuestion?id=1")).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})

    void edit1Question() throws Exception {
        doThrow(org.mockito.exceptions.base.MockitoException.class).when(questionService).find(anyInt());
        mockMvc.perform(get("/editQuestion?id=1")).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})

    void insertEditedQuestion() throws Exception {
        QuestionDTO question1 = new QuestionDTO();
        question1.setTitle("question1");
        question1.setId(1);
        question1.setDifficulty("easy");
        question1.setTag("que");
        List<OptionDTO> options = new ArrayList<>();
        OptionDTO option = new OptionDTO();
        option.setAnswer(true);
        option.setValue("que1");
        options.add(option);
        option.setAnswer(false);
        option.setValue("que2");
        options.add(option);
        option.setAnswer(false);
        option.setValue("que2");
        options.add(option);

        option.setAnswer(false);
        option.setValue("que2");
        options.add(option);

        question1.setOptions(options);

        when(questionService.find(anyInt())).thenReturn(question1);
        when(questionService.updateQuestion(questionDTO,questionDTO)).thenReturn(new QuestionDTO());

        mockMvc.perform(post("/insertEditedQuestion").contentType(MediaType.APPLICATION_FORM_URLENCODED)

                        .param("value", "value")
                        .param("isAnswer", "isAnswer")
                        .param("value", "value")
                        .param("isAnswer", "isAnswer")
                        .param("value", "value")
                        .param("isAnswer", "isAnswer")
                        .param("value", "value")
                        .param("isAnswer", "isAnswer")
                        .sessionAttr("question", new Question()))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})

    void insertEditedQuestion1() throws Exception {
        doThrow(org.mockito.exceptions.base.MockitoException.class).when(questionService).find(anyInt());

        mockMvc.perform(post("/insertEditedQuestion").contentType(MediaType.APPLICATION_FORM_URLENCODED)

                        .param("value", "value")
                        .param("isAnswer", "isAnswer")
                        .param("value", "value")
                        .param("isAnswer", "isAnswer")
                        .param("value", "value")
                        .param("isAnswer", "isAnswer")
                        .param("value", "value")
                        .param("isAnswer", "isAnswer")
                        .sessionAttr("question", new Question()))
                .andExpect(status().isOk());

    }

}
