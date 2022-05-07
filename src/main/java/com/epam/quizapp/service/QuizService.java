package com.epam.quizapp.service;

import com.epam.quizapp.data.*;
import com.epam.quizapp.exception.*;
import com.epam.quizapp.repository.QuizRepository;

import org.apache.logging.log4j.LogManager;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuizService {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(QuizService.class);
    @Autowired
    QuizRepository quizRepository;
    ModelMapper modelMapper = new ModelMapper();

    public QuizDTO addQuiz(QuizDTO quizDTO) {
        Quiz quiz;
        try {
            quiz = quizRepository.save(modelMapper.map(quizDTO, Quiz.class));
            LOGGER.info("Quiz added");
        } catch (Exception e) {
            LOGGER.error("Quiz not added ");
            throw new QuizException("Quiz cant saved of id" + quizDTO.getId());
        }
        return modelMapper.map(quiz, QuizDTO.class);

    }

    public QuizDTO find(int id) {
        return modelMapper.map(quizRepository.findById(id).orElseThrow(() -> new QuizNotFoundException("Quiz Not found with id " + id)), QuizDTO.class);
    }

    public void removeQuiz(QuizDTO quizDTO) {
        Quiz quiz = modelMapper.map(quizDTO, Quiz.class);
        try {
            quizRepository.delete(quiz);
            LOGGER.info("Quiz removed");
        } catch (Exception e) {
            LOGGER.error("Quiz not removed ");
            throw new QuizException("Quiz cant saved of id " + quizDTO.getId());
        }

    }

    public List<QuizDTO> getQuiz() {

        List<Quiz> quizzes = (List<Quiz>) quizRepository.findAll();
        return modelMapper.map(quizzes, new TypeToken<List<QuizDTO>>() {
        }.getType());


    }

    public boolean isQuestionInQuiz(Quiz quiz, int questionId) {

        return quiz.getQuestions().stream()
                .anyMatch(q -> q.getId() == questionId);
    }

    public void addQuestion(QuizDTO quizDTO, QuestionDTO questionDTO) {
        Quiz quiz = modelMapper.map(quizDTO, Quiz.class);
        Question question = modelMapper.map(questionDTO, Question.class);
        boolean isPresent = isQuestionInQuiz(quiz, question.getId());
        if (isPresent) {
            throw new QuestionMapWithQuizException("Question " + questionDTO.getId() + " map with Quiz ");
        }
        try {

            quiz.getQuestions().add(question);

            quizRepository.save(quiz);
            LOGGER.info("Question added to quiz ");
        } catch (Exception e) {
            LOGGER.info("Question not added to quiz ");
            throw new QuizException("Question id " + questionDTO.getId() + "not added to quiz id " + quizDTO.getId() + "");
        }
    }

    public void removeQuestion(QuizDTO quizDTO, int questionNo) {
        Quiz quiz = modelMapper.map(quizDTO, Quiz.class);


        try {
            quiz.getQuestions().remove(questionNo - 1);
            quizRepository.save(quiz);

            LOGGER.info("Question removed to quiz ");
        } catch (IndexOutOfBoundsException e) {
            LOGGER.info("QuizQuestion not found ");
            throw new QuizQuestionNotFoundException("Quiz's Question no " + questionNo + " not found");

        } catch (Exception e) {
            LOGGER.info("Question not removed to quiz ");
            throw new QuizException("Question no " + questionNo + " not removed to quiz id " + quizDTO.getId() + "");
        }
    }


    public void updateQuestion(QuizDTO quizDTO, int quizQuestionNo, QuestionDTO questionDTO) {
        Quiz quiz = modelMapper.map(quizDTO, Quiz.class);
        Question question = modelMapper.map(questionDTO, Question.class);


        try {
            quiz.getQuestions().set(quizQuestionNo - 1, question);
            quizRepository.save(quiz);

            LOGGER.info("Question updated to quiz ");
        } catch (IndexOutOfBoundsException e) {
            LOGGER.info("QuizQuestion not found ");
            throw new QuizQuestionNotFoundException("QuizQuestion no. " + quizQuestionNo + " not found");

        } catch (Exception e) {
            LOGGER.info("Question not updated to quiz ");
            throw new QuizException("QuizQuestion no. " + quizQuestionNo + " not updated with question id " + questionDTO.getId() + " to quiz id " + quizDTO.getId() + "");

        }

    }
}