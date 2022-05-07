package com.epam.quizapp.controller;

import com.epam.quizapp.data.QuestionDTO;
import com.epam.quizapp.data.QuizDTO;
import com.epam.quizapp.exception.QuestionMapWithQuizException;
import com.epam.quizapp.exception.QuizException;
import com.epam.quizapp.exception.QuizNotFoundException;
import com.epam.quizapp.exception.QuizQuestionNotFoundException;
import com.epam.quizapp.service.QuestionService;
import com.epam.quizapp.service.QuizService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuizRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private QuizService quizService;

    QuestionDTO questionDTO;
    @Autowired
    ObjectMapper mapper;


    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void addQuiz() throws Exception {

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

                .content(this.mapper.writeValueAsString(new QuizDTO()));

        mockMvc.perform(mockRequest).andExpect(status().isAccepted());
    }


    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void getQuizzes() throws Exception {
        doThrow(QuizQuestionNotFoundException.class).when(quizService).removeQuiz(any());

        QuizDTO quizDTO1 = new QuizDTO();
        QuizDTO quizDTO2 = new QuizDTO();
        QuizDTO quizDTO3 = new QuizDTO();
        List<QuizDTO> quizzes = Arrays.asList(quizDTO1, quizDTO2, quizDTO3);

        when(quizService.getQuiz()).thenReturn(quizzes);
        mockMvc.perform(get("/quizzes")).andExpect(status().isAccepted());

    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void deleteQuestion() throws Exception {
        mockMvc.perform(delete("/quizzes/1")).andExpect(status().isNoContent());

    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void deleteQuestionNotFound() throws Exception {
        doThrow(QuizNotFoundException.class).when(quizService).removeQuiz(any());

        mockMvc.perform(delete("/quizzes/1")).andExpect(status().isBadRequest());

    }


    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void addQuestion() throws Exception {

        mockMvc.perform(post("/quizzes/1/questions/1"))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void removeQuestion() throws Exception {
        QuizDTO quiz = new QuizDTO();
        quiz.setQuestions(Arrays.asList(new QuestionDTO(), new QuestionDTO()));
        QuizDTO quiz1 = new QuizDTO();
        QuizDTO quiz2 = new QuizDTO();
        QuizDTO quiz3 = new QuizDTO();

        List<QuizDTO> quizzes = Arrays.asList(quiz1, quiz2, quiz3);

        when(quizService.getQuiz()).thenReturn(quizzes);
        when(quizService.find(1)).thenReturn(quiz);

        mockMvc.perform(delete("/quizzes/1/questions/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void removeQuestionNotFound() throws Exception {
        doThrow(QuizQuestionNotFoundException.class).when(quizService).removeQuestion(any(), anyInt());
        QuizDTO quiz = new QuizDTO();
        quiz.setQuestions(Arrays.asList(new QuestionDTO(), new QuestionDTO()));
        QuizDTO quiz1 = new QuizDTO();
        QuizDTO quiz2 = new QuizDTO();
        QuizDTO quiz3 = new QuizDTO();

        List<QuizDTO> quizzes = Arrays.asList(quiz1, quiz2, quiz3);

        when(quizService.getQuiz()).thenReturn(quizzes);
        when(quizService.find(1)).thenReturn(quiz);

        mockMvc.perform(delete("/quizzes/1/questions/1"))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void removeNull() throws Exception {
        doThrow(QuizException.class).when(quizService).removeQuestion(any(), anyInt());
        QuizDTO quiz = new QuizDTO();
        quiz.setQuestions(Arrays.asList(new QuestionDTO(), new QuestionDTO()));
        QuizDTO quiz1 = new QuizDTO();
        QuizDTO quiz2 = new QuizDTO();
        QuizDTO quiz3 = new QuizDTO();

        List<QuizDTO> quizzes = Arrays.asList(quiz1, quiz2, quiz3);

        when(quizService.getQuiz()).thenReturn(quizzes);
        when(quizService.find(1)).thenReturn(quiz);

        mockMvc.perform(delete("/quizzes/1/questions/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void updateQuestion() throws Exception {
        QuizDTO quiz = new QuizDTO();
        quiz.setQuestions(Arrays.asList(new QuestionDTO(), new QuestionDTO()));
        when(quizService.find(1)).thenReturn(quiz);

        QuestionDTO question1 = new QuestionDTO();
        QuestionDTO question2 = new QuestionDTO();
        QuestionDTO question3 = new QuestionDTO();
        List<QuestionDTO> questions = Arrays.asList(question1, question2, question3);

        when(questionService.getQuestion()).thenReturn(questions);
        mockMvc.perform(put("/quizzes/1/questions/1/1"))
                .andExpect(status().isOk());
    }
}