package com.epam.quizapp.controller;

import java.util.ArrayList;
import java.util.List;

import com.epam.quizapp.data.OptionDTO;
import com.epam.quizapp.data.QuestionDTO;
import com.epam.quizapp.exception.QuestionMapWithQuizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.epam.quizapp.service.QuestionService;



@Controller
public class QuestionController {

    private static final String VIEW_QUESTIONS = "viewQuestions";
    private static final String QUESTIONS = "questions";
    private static final String MESSAGE = "message";
    private static final String INVALID_REQUEST = "Invalid Request!";


    @Autowired
    QuestionService questionService;

    @RequestMapping(VIEW_QUESTIONS)
    public ModelAndView getQuestions() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(VIEW_QUESTIONS);
        modelAndView.addObject(QUESTIONS, questionService.getQuestion());
        return modelAndView;
    }

    @PostMapping("insertQuestion")
    public ModelAndView addQuestion(QuestionDTO questionDTO, String[] value, String[] isAnswer) {

        ModelAndView modelView = new ModelAndView();
        List<OptionDTO> optionsDTO = new ArrayList<>();
        for (int i = 0; i < value.length; i++) {
            OptionDTO optionDTO = new OptionDTO();
            optionDTO.setAnswer(Boolean.parseBoolean(isAnswer[i]));
            optionDTO.setValue(value[i]);
            optionsDTO.add(optionDTO);

        }
        questionDTO.setOptions(optionsDTO);
        questionService.addQuestion(questionDTO);
        modelView.addObject(MESSAGE, "Question Saved Successfully!!!!");
        modelView.setViewName("createQuestion");

        return modelView;
    }

    @RequestMapping("deleteQuestion")
    public ModelAndView deleteQuestion(@RequestParam("id") int id) {
        ModelAndView modelView = new ModelAndView();
        try {
            QuestionDTO questionDTO = questionService.find(id);
            questionService.removeQuestion(questionDTO);
            modelView.addObject(MESSAGE, "Question Deleted Successfully!!!!");


        } catch (QuestionMapWithQuizException e) {
            modelView.addObject(MESSAGE, "Question map with quiz");

        } catch (Exception e) {
            modelView.addObject(MESSAGE, INVALID_REQUEST);

        } finally {
            modelView.addObject(QUESTIONS, questionService.getQuestion());
            modelView.setViewName(VIEW_QUESTIONS);
        }
        return modelView;


    }

    @RequestMapping("editQuestion")
    public ModelAndView editQuestion(@RequestParam("id") int id) {
        ModelAndView modelView = new ModelAndView();

        try {
            QuestionDTO questionDTO = questionService.find(id);
            modelView.addObject("question", questionDTO);
            modelView.setViewName("editQuestion");
        } catch (Exception e) {
            modelView.setViewName(VIEW_QUESTIONS);
            modelView.addObject(QUESTIONS, questionService.getQuestion());
            modelView.addObject(MESSAGE, INVALID_REQUEST);


        }
        return modelView;


    }

    @RequestMapping("insertEditedQuestion")
    public ModelAndView insertEditedQuestion(QuestionDTO questionDTO, String[] value, String[] isAnswer) {


        ModelAndView modelView = new ModelAndView();
        try {
            QuestionDTO foundQuestion = questionService.find(questionDTO.getId());
            List<OptionDTO> optionDTOS = new ArrayList<>();
            int optionsNo = foundQuestion.getOptions().size();
            for (int i = 0; i < optionsNo; i++) {
                OptionDTO optionDTO = new OptionDTO();
                optionDTO.setAnswer(Boolean.parseBoolean(isAnswer[i]));
                optionDTO.setValue(value[i]);
                optionDTOS.add(optionDTO);

            }
            questionDTO.setOptions(optionDTOS);
            questionService.updateQuestion(questionDTO,foundQuestion);
            modelView.addObject(MESSAGE, "Question Updated Successfully!!!!");
            modelView.setViewName(VIEW_QUESTIONS);
            modelView.addObject(QUESTIONS, questionService.getQuestion());
        } catch (Exception e) {

            modelView.addObject(MESSAGE, INVALID_REQUEST);
            modelView.setViewName(VIEW_QUESTIONS);
            modelView.addObject(QUESTIONS, questionService.getQuestion());

        }
        return modelView;

    }

}
