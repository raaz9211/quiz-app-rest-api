package com.epam.quizapp.service;

import com.epam.quizapp.data.Option;
import com.epam.quizapp.data.OptionDTO;
import com.epam.quizapp.data.Question;
import com.epam.quizapp.data.QuestionDTO;
import com.epam.quizapp.repository.QuestionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {
    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    ModelMapper modelMapper;

    Question question1 = new Question();
    Question question2 = new Question();

    QuestionDTO questionDTO = new QuestionDTO();


    @BeforeEach
    void setUp() {

        question1.setTitle("question1");
        question1.setId(1);
        question1.setDifficulty("easy");
        question1.setTag("que");
        List<Option> options = new ArrayList<>();
        Option option = new Option();
        option.setQuestion(question1);
        option.setAnswer(true);
        option.setValue("que1");
        options.add(option);
        option.setQuestion(question1);
        option.setAnswer(false);
        option.setValue("que2");
        options.add(option);
        option.setQuestion(question1);
        option.setAnswer(false);
        option.setValue("que2");
        options.add(option);
        option.setQuestion(question1);
        option.setAnswer(false);
        option.setValue("que2");
        options.add(option);
        question1.setOptions(options);

        question2.setTitle("question2");
        question2.setId(2);
        question2.setDifficulty("easy");
        question2.setTag("que");
        question2.setOptions(options);


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
    void addQuestion() {

        questionService.addQuestion(questionDTO);
        verify(questionRepository).save(any());
    }

    @Test
    void addNullQuestion() {
        doThrow(org.mockito.exceptions.base.MockitoException.class).when(questionRepository).save(null);
        try {
            questionService.addQuestion(questionDTO);
        } catch (Exception e) {

        }
        verify(questionRepository).save(any());

    }


    @Test
    void removeQuestion() {

        when(modelMapper.map(any(), any())).thenReturn(question1);
        questionService.removeQuestion(questionDTO);
        verify(questionRepository).delete(any());
    }

    @Test
    void removeNullQuestion() {


        doThrow(org.mockito.exceptions.base.MockitoException.class).when(questionRepository).delete(any());
        when(modelMapper.map(any(), any())).thenReturn(question1);
        try {
            questionService.removeQuestion(questionDTO);
        } catch (Exception e) {

        }
        verify(questionRepository).delete(any());

    }

    @Test
    void removeMappedQuestion() {

        doThrow(DataIntegrityViolationException.class).when(questionRepository).delete(null);
        try {


            questionService.removeQuestion(questionDTO);
        } catch (Exception e) {
            System.out.println(e);
        }
        verify(questionRepository).delete(null);


    }


    @Test
    void updateQuestion() {
        questionService.updateQuestion(questionDTO,questionDTO);
        verify(questionRepository).save(any());
    }

    @Test
    void updateNullQuestion() {
        doThrow(org.mockito.exceptions.base.MockitoException.class).when(questionRepository).save(any());
        when(modelMapper.map(any(), any())).thenReturn(question1);
        try {


            questionService.updateQuestion(questionDTO,questionDTO);
        } catch (Exception e) {

        }
        verify(questionRepository).save(any());

    }

    @Test
    void testGetQuestion() {


        List<Question> questionsMock = Arrays.asList(question1, question2);
        List<QuestionDTO> questionsDTOMock = Arrays.asList(questionDTO, questionDTO);

        given(questionRepository.findAll()).willReturn(questionsMock);
        when(modelMapper.map(questionsMock, new TypeToken<List<QuestionDTO>>() {
        }.getType())).thenReturn(questionsDTOMock);
        List<QuestionDTO> questions = questionService.getQuestion();
        assertEquals(2, questions.size());
    }

    @Test
    void testEmptyGetQuestion() {
        List<Question> questionsMock = new ArrayList<>();
        List<QuestionDTO> questionsDTOMock = Arrays.asList();

        given(questionRepository.findAll()).willReturn(questionsMock);
        when(modelMapper.map(questionsMock, new TypeToken<List<QuestionDTO>>() {
        }.getType())).thenReturn(questionsDTOMock);


        List<QuestionDTO> questions = questionService.getQuestion();
        assertEquals(0, questions.size());
    }

    @Test
    void testFindQuestion() {
        Optional<Question> question = Optional.ofNullable(new Question());
        question.get().setTitle("question1");
        question.get().setId(1);
        question.get().setDifficulty("easy");
        question.get().setTag("que");
        List<Option> options = new ArrayList<>();
        Option option = new Option();
        option.setQuestion(question.get());
        option.setAnswer(true);
        option.setValue("que1");
        options.add(option);
        option.setQuestion(question.get());
        option.setAnswer(false);
        option.setValue("que2");
        options.add(option);
        question.get().setOptions(options);

        given(questionRepository.findById(anyInt())).willReturn(question);
        when(modelMapper.map(any(), any())).thenReturn(questionDTO);

        QuestionDTO foundQuestion = questionService.find(anyInt());
        assertEquals(question.get().getTitle(), foundQuestion.getTitle());
    }

    @Test
    void testNullFindQuestion() {
        Optional<Question> question = Optional.ofNullable(null);

        given(questionRepository.findById(anyInt())).willReturn(question);
        QuestionDTO foundQuestion = null;
        try {


            foundQuestion = questionService.find(anyInt());
        } catch (Exception e) {
            System.out.println(e);
        }
        assertNull(foundQuestion);
    }

    @Test
    void setObject() {
        assertNotNull(questionService);

    }


}