package com.epam.quizapp.controller;

import java.util.List;

import com.epam.quizapp.data.*;
import com.epam.quizapp.exception.QuestionMapWithQuizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.epam.quizapp.service.QuestionService;
import com.epam.quizapp.service.QuizService;


@Controller
public class QuizController {

    private static final String VIEW_QUIZZES = "viewQuizzes";
    private static final String QUIZZES = "quizzes";
    private static final String MESSAGE = "message";
    private static final String QUESTIONS = "questions";
    private static final String QUIZ_ID = "quizId";
    private static final String VIEW_QUIZ_QUESTIONS = "viewQuizQuestions";
    private static final String INVALID_REQUEST = "Invalid Request!";

    @Autowired
    QuizService quizService;

    @Autowired
    QuestionService questionService;

    @RequestMapping(VIEW_QUIZZES)
    public ModelAndView viewQuizzes() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(VIEW_QUIZZES);

        modelAndView.addObject(QUIZZES, quizService.getQuiz());

        return modelAndView;

    }

    @PostMapping("insertQuiz")
    public ModelAndView insertQuiz(QuizDTO quizDTO) {

        ModelAndView modelView = new ModelAndView();
        quizService.addQuiz(quizDTO);
        modelView.addObject(MESSAGE, "Quiz Saved Successfully!!!!");


        modelView.setViewName("createQuiz");

        return modelView;
    }

    @RequestMapping("deleteQuiz")
    public ModelAndView deleteQuestion(@RequestParam("id") int id) {
        ModelAndView modelView = new ModelAndView();
        try {
            QuizDTO quizDTO = quizService.find(id);
            quizService.removeQuiz(quizDTO);
            modelView.addObject(MESSAGE, "Quiz Deleted Successfully!!!!");
        } catch (Exception e) {
            modelView.addObject(MESSAGE, INVALID_REQUEST);
        } finally {

            modelView.setViewName(VIEW_QUIZZES);
            modelView.addObject(QUIZZES, quizService.getQuiz());

        }
        return modelView;


    }

    @RequestMapping("getQuestions")
    public ModelAndView getQuestions(@RequestParam("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("getQuestions");
        modelAndView.addObject(QUESTIONS, questionService.getQuestion());
        modelAndView.addObject(QUIZ_ID, id);
        return modelAndView;
    }


    @RequestMapping("addQuizQuestion")
    public ModelAndView addQuizQuestion(@RequestParam("id") int id, @RequestParam("quizId") int quizId) {
        ModelAndView modelView = new ModelAndView();


        try {
            QuizDTO quizDTO = quizService.find(quizId);
            QuestionDTO questionDTO = questionService.find(id);

            quizService.addQuestion(quizDTO, questionDTO);
            modelView.addObject(MESSAGE, "Question Added Successfully!!!!");

        } catch (QuestionMapWithQuizException e) {
            modelView.addObject(MESSAGE, "Question map with quiz");

        } catch (Exception e) {
            modelView.addObject(MESSAGE, INVALID_REQUEST);

        } finally {


            modelView.addObject(QUIZZES, quizService.getQuiz());
            modelView.setViewName(VIEW_QUIZZES);

        }
        return modelView;

    }

    @RequestMapping(VIEW_QUIZ_QUESTIONS)
    public ModelAndView viewQuizQuestion(@RequestParam("id") int id) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            QuizDTO quizDTO = quizService.find(id);

            List<QuestionDTO> questionsDTO = quizDTO.getQuestions();
            modelAndView.addObject(QUESTIONS, questionsDTO);
            modelAndView.addObject(QUIZ_ID, id);
            modelAndView.setViewName(VIEW_QUIZ_QUESTIONS);
        } catch (Exception e) {

            modelAndView.setViewName(VIEW_QUIZZES);
            modelAndView.addObject(MESSAGE, INVALID_REQUEST);

        }
        return modelAndView;

    }

    @RequestMapping("removeQuizQuestion")
    public ModelAndView removeQuizQuestion(@RequestParam("id") int id, @RequestParam("quizId") int quizId) {
        ModelAndView modelView = new ModelAndView();

        try {
            QuizDTO quizDTO = quizService.find(quizId);

            quizService.removeQuestion(quizDTO, id + 1);
            modelView.addObject(MESSAGE, "Question removed Successfully!!!!");
            modelView.addObject(QUESTIONS, quizService.find(quizId).getQuestions());
            modelView.addObject(QUIZ_ID, quizId);
            modelView.setViewName(VIEW_QUIZ_QUESTIONS);


        } catch (Exception e) {
            modelView.addObject(MESSAGE, INVALID_REQUEST);
            modelView.setViewName(VIEW_QUIZZES);
            modelView.addObject(QUIZZES, quizService.getQuiz());


        }
        return modelView;

    }

    @RequestMapping("getQuizQuestions")
    public ModelAndView getQuizQuestions(@RequestParam("questionNo") int questionNo, @RequestParam("quizId") int quizId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("getQuizQuestions");
        modelAndView.addObject(QUESTIONS, questionService.getQuestion());
        modelAndView.addObject(QUIZ_ID, quizId);
        modelAndView.addObject("questionNo", questionNo);

        return modelAndView;
    }

    @RequestMapping("updateQuizQuestion")
    public ModelAndView updateQuizQuestion(@RequestParam("id") int id, @RequestParam("questionNo") int questionNo, @RequestParam("quizId") int quizId) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            QuizDTO quizDTO = quizService.find(quizId);
            QuestionDTO questionDTO = questionService.find(id);
            quizService.updateQuestion(quizDTO, questionNo + 1, questionDTO);
            modelAndView.addObject(QUESTIONS, quizService.find(quizId).getQuestions());
            modelAndView.addObject(QUIZ_ID, quizId);
            modelAndView.addObject(MESSAGE, "Question Updated Successfully!!!!");
            modelAndView.setViewName(VIEW_QUIZ_QUESTIONS);

        } catch (Exception e) {
            modelAndView.addObject(MESSAGE, INVALID_REQUEST);
            modelAndView.setViewName(VIEW_QUIZZES);
            modelAndView.addObject(QUIZZES, quizService.getQuiz());


        }
        return modelAndView;


    }


}
