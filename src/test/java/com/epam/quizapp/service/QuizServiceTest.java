package com.epam.quizapp.service;

import com.epam.quizapp.data.*;
import com.epam.quizapp.repository.QuizRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private QuizService quizService;
    @Mock
    private QuizService quizService1;

    QuestionDTO questionDTO = new QuestionDTO();
    Question question1 = new Question();

    QuizDTO quizDTO = new QuizDTO();
    Quiz quiz = new Quiz();


    @BeforeEach
    void setUp() {

        List<QuestionDTO> questionsDTO = new ArrayList<>();
        questionsDTO.add(new QuestionDTO());
        quizDTO.setQuestions(questionsDTO);

        List<Question> questions = new ArrayList<>();
        questions.add(new Question());
        quiz.setQuestions(questions);

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
    void addQuiz() {
        quizService.addQuiz(new QuizDTO());
        verify(quizRepository).save(any());
    }

    @Test
    void addNullQuiz() {
        doThrow(org.mockito.exceptions.base.MockitoException.class).when(quizRepository).save(null);
        try {


            quizService.addQuiz(new QuizDTO());
        } catch (Exception e) {

        }
        verify(quizRepository).save(any());
    }

    @Test
    void removeQuiz() {
        when(modelMapper.map(any(), any())).thenReturn(new Quiz());
        quizService.removeQuiz(new QuizDTO());
        verify(quizRepository).delete(any());
    }

    @Test
    void removeNullQuiz() {

        doThrow(org.mockito.exceptions.base.MockitoException.class).when(quizRepository).delete(any());
        try {
            quizService.removeQuiz(quizDTO);
        } catch (Exception e) {

        }
        verify(quizRepository).delete(any());
    }

    @Test
    void getQuiz() {
        Quiz quiz = new Quiz();
        quiz.setId(1);
        quiz.setName("java");
        Quiz quiz1 = new Quiz();
        quiz.setId(2);
        quiz.setName("java");

        List<Quiz> quizzesMock = Arrays.asList(quiz, quiz1);
        given(quizRepository.findAll()).willReturn(quizzesMock);
        List<QuizDTO> quizzes = Arrays.asList(new QuizDTO(), new QuizDTO());
        when(modelMapper.map(quizzesMock, new TypeToken<List<QuizDTO>>() {
        }.getType())).thenReturn(quizzes);

        List<QuizDTO> foundQuizzes = quizService.getQuiz();
        assertEquals(2, foundQuizzes.size());
    }

    @Test
    void getNullQuiz() {
        List<Quiz> quizzesMock = new ArrayList<>();
        given(quizRepository.findAll()).willReturn(quizzesMock);
        List<QuizDTO> quizzes = Arrays.asList();

        when(modelMapper.map(quizzesMock, new TypeToken<List<QuizDTO>>() {
        }.getType())).thenReturn(quizzes);

        List<QuizDTO> foundQuizzes = quizService.getQuiz();
        assertEquals(0, foundQuizzes.size());
    }


    @Test
    void addQuestion() {


        when(modelMapper.map(quizDTO, Quiz.class)).thenReturn(quiz);
        when(modelMapper.map(questionDTO, Question.class)).thenReturn(question1);
        quizService.addQuestion(quizDTO, questionDTO);
        verify(quizRepository).save(any());
    }

    @Test
    void addMappedQuestion() {
        quiz.getQuestions().add(question1);

        when(modelMapper.map(quizDTO, Quiz.class)).thenReturn(quiz);
        when(modelMapper.map(questionDTO, Question.class)).thenReturn(question1);
        quizDTO.getQuestions().add(questionDTO);
        try {
            quizService.addQuestion(quizDTO, questionDTO);
            verify(quizRepository).save(any());

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    void addNullQuestion() {

        when(modelMapper.map(quizDTO, Quiz.class)).thenReturn(quiz);
        when(modelMapper.map(questionDTO, Question.class)).thenReturn(question1);
        doThrow(org.mockito.exceptions.base.MockitoException.class).when(quizRepository).save(any());

        try {
            quizService.addQuestion(quizDTO, questionDTO);
        } catch (Exception e) {

        }
        verify(quizRepository).save(any());
    }


    @Test
    void removeQuestion() {
        when(modelMapper.map(quizDTO, Quiz.class)).thenReturn(quiz);

        quizService.removeQuestion(quizDTO, 1);
        verify(quizRepository).save(any());
    }

    @Test
    void removeNullQuestion() {

        when(modelMapper.map(quizDTO, Quiz.class)).thenReturn(quiz);

        doThrow(IndexOutOfBoundsException.class).when(quizRepository).save(any());
        try {


            quizService.removeQuestion(quizDTO, 1);
        } catch (Exception e) {

        }
        verify(quizRepository).save(any());
    }

    @Test
    void removeNull1Question() {

        when(modelMapper.map(quizDTO, Quiz.class)).thenReturn(quiz);

        doThrow(org.mockito.exceptions.base.MockitoException.class).when(quizRepository).save(any());
        try {


            quizService.removeQuestion(quizDTO, 1);
        } catch (Exception e) {

        }
        verify(quizRepository).save(any());
    }


    @Test
    void updateQuestion() {

        when(modelMapper.map(quizDTO, Quiz.class)).thenReturn(quiz);
        when(modelMapper.map(questionDTO, Question.class)).thenReturn(question1);

        quizService.updateQuestion(quizDTO, 1, questionDTO);
        verify(quizRepository).save(any());
    }

    @Test
    void updateNullQuestion() {
        when(modelMapper.map(quizDTO, Quiz.class)).thenReturn(quiz);
        when(modelMapper.map(questionDTO, Question.class)).thenReturn(question1);


        doThrow(org.mockito.exceptions.base.MockitoException.class).when(quizRepository).save(any());
        try {


            quizService.updateQuestion(quizDTO, 1, questionDTO);
        } catch (Exception e) {

        }
        verify(quizRepository).save(any());
    }

    @Test
    void updateNull1Question() {
        when(modelMapper.map(quizDTO, Quiz.class)).thenReturn(quiz);
        when(modelMapper.map(questionDTO, Question.class)).thenReturn(question1);


        doThrow(IndexOutOfBoundsException.class).when(quizRepository).save(any());
        try {


            quizService.updateQuestion(quizDTO, 1, questionDTO);
        } catch (Exception e) {

        }
        verify(quizRepository).save(any());
    }

    @Test
    void findQuiz() {
        Optional<Quiz> quiz = Optional.ofNullable(new Quiz());
        given(quizRepository.findById(anyInt())).willReturn(quiz);
        QuizDTO quizDTO = quizService.find(anyInt());
        assertNull(quizDTO);


    }

    @Test
    void findQuizNull() {
        Optional<Quiz> quiz = Optional.ofNullable(null);
        given(quizRepository.findById(anyInt())).willReturn(quiz);
        QuizDTO quizDTOTest = null;
        try {
            quizDTOTest = quizService.find(anyInt());
        } catch (Exception e) {
            System.out.println(e);
        }
        assertNull(quizDTOTest);


    }


    @Test
    void setObject() {
        assertNotNull(quizService);

    }


}