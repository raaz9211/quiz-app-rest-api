package com.epam.quizapp.service;

import com.epam.quizapp.data.*;
import com.epam.quizapp.exception.*;
import com.epam.quizapp.repository.QuestionRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class QuestionService {
    private static final Logger LOGGER = LogManager.getLogger(QuestionService.class);
    @Autowired
    QuestionRepository questionRepository;
    ModelMapper modelMapper = new ModelMapper();

    public QuestionDTO addQuestion(QuestionDTO questionDTO) {

        Question question;
        try {
            question = questionRepository.save(modelMapper.map(questionDTO, Question.class));
            LOGGER.info("Question added");
        } catch (Exception e) {
            LOGGER.error("Add a valid question ");
            throw new QuestionException("Question cant saved");
        }

        return modelMapper.map(question, QuestionDTO.class);
    }

    public void removeQuestion(QuestionDTO questionDTO) {
        Question question = modelMapper.map(questionDTO, Question.class);

        try {
            questionRepository.delete(question);
            LOGGER.info("Question removed ");

        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Question is mapped with quiz ");
            throw new QuestionMapWithQuizException("Question " + questionDTO.getId() + " map with Quiz ");
        } catch (Exception e) {

            LOGGER.error(e);
            LOGGER.error("Enter valid question code ");
            throw new QuestionException("Question cant removed ");

        }
    }


    public QuestionDTO updateQuestion(QuestionDTO questionDTO,QuestionDTO foundQuestion) {

        Question question;
        try {

            questionDTO.setId(foundQuestion.getId());
            questionDTO.getOptions().get(0).setId(foundQuestion.getOptions().get(0).getId());
            questionDTO.getOptions().get(1).setId(foundQuestion.getOptions().get(1).getId());
            questionDTO.getOptions().get(2).setId(foundQuestion.getOptions().get(2).getId());
            questionDTO.getOptions().get(3).setId(foundQuestion.getOptions().get(3).getId());
            question = questionRepository.save(modelMapper.map(questionDTO, Question.class));
            LOGGER.info("Question updated ");
        } catch (Exception e) {

            LOGGER.error("Enter valid question code ");
            throw new QuestionException("Question cant Updated");

        }
        return modelMapper.map(question, QuestionDTO.class);

    }

    public List<QuestionDTO> getQuestion() {

        List<Question> questions = (List<Question>) questionRepository.findAll();
        return modelMapper.map(questions, new TypeToken<List<QuestionDTO>>() {
        }.getType());
    }

    public QuestionDTO find(int id) {
        Optional<Question> question  = questionRepository.findById(id);
        return modelMapper.map(question
                .orElseThrow(() -> new QuestionNotFoundException("Question Not found with id " + id)), QuestionDTO.class);
    }

}
