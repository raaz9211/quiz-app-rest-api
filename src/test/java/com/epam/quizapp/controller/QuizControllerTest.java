package com.epam.quizapp.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import com.epam.quizapp.data.QuestionDTO;
import com.epam.quizapp.data.QuizDTO;
import com.epam.quizapp.exception.QuestionMapWithQuizException;
import com.epam.quizapp.service.AdminService;
import com.epam.quizapp.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.quizapp.data.Quiz;
import com.epam.quizapp.service.QuestionService;
import com.epam.quizapp.service.QuizService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = QuizController.class)
class QuizControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;
    @MockBean
    private QuestionService questionService;
    @MockBean
    JwtUtil jwtUtil;
    @MockBean
    AdminService adminService;

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})

    void viewQuiz() throws Exception {
        QuizDTO quiz1 = new QuizDTO();
        QuizDTO quiz2 = new QuizDTO();
        QuizDTO quiz3 = new QuizDTO();

        List<QuizDTO> quizzes = Arrays.asList(quiz1, quiz2, quiz3);

        when(quizService.getQuiz()).thenReturn(quizzes);
        mockMvc.perform(get("/viewQuizzes"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})

    void insertQuiz() throws Exception {

        mockMvc.perform(post("/insertQuiz")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr("quiz", new Quiz()))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})

    void deleteQuiz() throws Exception {
        when(quizService.find(anyInt())).thenReturn(new QuizDTO());
        mockMvc.perform(get("/deleteQuiz?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})

    void deleteNullQuiz() throws Exception {
        doThrow(org.mockito.exceptions.base.MockitoException.class).when(quizService).find(anyInt());

        mockMvc.perform(get("/deleteQuiz?id=1"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})

    void getQuestions() throws Exception {
        QuestionDTO question1 = new QuestionDTO();
        QuestionDTO question2 = new QuestionDTO();
        QuestionDTO question3 = new QuestionDTO();
        List<QuestionDTO> questions = Arrays.asList(question1, question2, question3);

        when(questionService.getQuestion()).thenReturn(questions);
        mockMvc.perform(get("/getQuestions?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void addQuizQuestion() throws Exception {
        when(questionService.find(anyInt())).thenReturn(new QuestionDTO());
        when(quizService.find(anyInt())).thenReturn(new QuizDTO());

        mockMvc.perform(get("/addQuizQuestion?id=1&quizId=1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void addQuizQuestion1() throws Exception {
        doThrow(QuestionMapWithQuizException.class).when(quizService).find(anyInt());

        mockMvc.perform(get("/addQuizQuestion?id=1&quizId=1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void addErrorQuizQuestion() throws Exception {
        doThrow(org.mockito.exceptions.base.MockitoException.class).when(quizService).find(anyInt());

        mockMvc.perform(get("/addQuizQuestion?id=1&quizId=1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void viewQuizQuestions() throws Exception {
        QuizDTO quiz = new QuizDTO();
        quiz.setQuestions(Arrays.asList(new QuestionDTO(), new QuestionDTO()));
        when(quizService.find(1)).thenReturn(quiz);

        mockMvc.perform(get("/viewQuizQuestions?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void viewQuizQuestions1() throws Exception {
        when(quizService.find(1)).thenReturn(null);

        mockMvc.perform(get("/viewQuizQuestions?id=1"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void removeQuizQuestion() throws Exception {
        QuizDTO quiz = new QuizDTO();
        quiz.setQuestions(Arrays.asList(new QuestionDTO(), new QuestionDTO()));
        QuizDTO quiz1 = new QuizDTO();
        QuizDTO quiz2 = new QuizDTO();
        QuizDTO quiz3 = new QuizDTO();

        List<QuizDTO> quizzes = Arrays.asList(quiz1, quiz2, quiz3);

        when(quizService.getQuiz()).thenReturn(quizzes);
        when(quizService.find(1)).thenReturn(quiz);

        mockMvc.perform(get("/removeQuizQuestion?id=1&quizId=1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void removeErrorQuizQuestion() throws Exception {

        doThrow(org.mockito.exceptions.base.MockitoException.class).when(quizService).find(anyInt());

        mockMvc.perform(get("/removeQuizQuestion?id=1&quizId=1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void getQuizQuestions() throws Exception {
        QuestionDTO question1 = new QuestionDTO();
        QuestionDTO question2 = new QuestionDTO();
        QuestionDTO question3 = new QuestionDTO();
        List<QuestionDTO> questions = Arrays.asList(question1, question2, question3);

        when(questionService.getQuestion()).thenReturn(questions);
        mockMvc.perform(get("/getQuizQuestions?questionNo=1&quizId=1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void updateQuizQuestion() throws Exception {
        QuizDTO quiz = new QuizDTO();
        quiz.setQuestions(Arrays.asList(new QuestionDTO(), new QuestionDTO()));
        when(quizService.find(anyInt())).thenReturn(quiz);
        when(questionService.find(anyInt())).thenReturn(new QuestionDTO());

        QuestionDTO question1 = new QuestionDTO();
        QuestionDTO question2 = new QuestionDTO();
        QuestionDTO question3 = new QuestionDTO();
        List<QuestionDTO> questions = Arrays.asList(question1, question2, question3);

        when(questionService.getQuestion()).thenReturn(questions);
        mockMvc.perform(get("/updateQuizQuestion?id=1&questionNo=1&quizId=1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "qwerty",roles = {"USER","ADMIN"})
    void updateQuizQuestion1() throws Exception {
        QuizDTO quiz = new QuizDTO();
        quiz.setQuestions(Arrays.asList(new QuestionDTO(), new QuestionDTO()));
        when(quizService.find(anyInt())).thenReturn(null);
        when(questionService.find(anyInt())).thenReturn(null);

        QuestionDTO question1 = new QuestionDTO();
        QuestionDTO question2 = new QuestionDTO();
        QuestionDTO question3 = new QuestionDTO();
        List<QuestionDTO> questions = Arrays.asList(question1, question2, question3);

        when(questionService.getQuestion()).thenReturn(questions);
        mockMvc.perform(get("/updateQuizQuestion?id=1&questionNo=1&quizId=1"))
                .andExpect(status().isOk());
    }


}
