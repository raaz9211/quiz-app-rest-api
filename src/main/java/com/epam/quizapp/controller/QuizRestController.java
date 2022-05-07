package com.epam.quizapp.controller;

import com.epam.quizapp.data.QuestionDTO;
import com.epam.quizapp.data.QuizDTO;
import com.epam.quizapp.service.QuestionService;
import com.epam.quizapp.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class QuizRestController {

    @Autowired
    QuestionService questionService;
    @Autowired
    QuizService quizService;

    @Operation(description = "Create Quiz")
    @ApiResponse(responseCode = "201",description = "Successfully created")
    @ApiResponse(responseCode = "400",description = "Not created")
    @PostMapping("quizzes")
    public ResponseEntity<QuizDTO> addQuiz(@RequestBody @Valid QuizDTO quizDTO) {

        return new ResponseEntity<>(quizService.addQuiz(quizDTO), HttpStatus.ACCEPTED);
    }

    @Operation(description = "Get all quizzes")
    @ApiResponse(responseCode = "202",description = "Successfully fetched")
    @GetMapping("quizzes")
    public ResponseEntity<List<QuizDTO>> getQuizzes() {
        return new ResponseEntity<>(quizService.getQuiz(), HttpStatus.ACCEPTED);
    }


    @Operation(description = "Delete quizzes")
    @ApiResponse(responseCode = "204",description = "Successfully deleted")
    @ApiResponse(responseCode = "400",description = "Not deleted")
    @DeleteMapping("quizzes/{id}")
    public ResponseEntity<Integer> deleteQuestion(@PathVariable("id") int id) {
        QuizDTO quizDTO = quizService.find(id);
        quizService.removeQuiz(quizDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    @Operation(description = "Add question")
    @ApiResponse(responseCode = "201",description = "Successfully Added")
    @ApiResponse(responseCode = "400",description = "Not Added")
    @PostMapping("quizzes/{id}/questions/{questionId}")
    public ResponseEntity<QuizDTO> addQuestion(@PathVariable("id") int id, @PathVariable("questionId") int questionId) {

        QuizDTO quizDTO = quizService.find(id);
        QuestionDTO questionDTO = questionService.find(questionId);
        quizService.addQuestion(quizDTO, questionDTO);
        return new ResponseEntity<>(quizService.find(id), HttpStatus.ACCEPTED);


    }


    @Operation(description = "Remove quistion")
    @ApiResponse(responseCode = "204",description = "Successfully removed")
    @ApiResponse(responseCode = "400",description = "Not removed")
    @DeleteMapping("quizzes/{id}/questions/{questionNo}")
    public void removeQuestion(@PathVariable("id") int id, @PathVariable("questionNo") int questionNo) {

        QuizDTO quizDTO = quizService.find(id);
        quizService.removeQuestion(quizDTO, questionNo);

    }


    @Operation(description = "Update question")
    @ApiResponse(responseCode = "204",description = "Successfully Updated")
    @ApiResponse(responseCode = "400",description = "Not Updated")
    @PutMapping("quizzes/{id}/questions/{questionNo}/{questionId}")
    public void updateQuestion(@PathVariable("id") int id, @PathVariable("questionNo") int questionNo, @PathVariable("questionId") int questionId) {

        QuizDTO quizDTO = quizService.find(id);
        QuestionDTO questionDTO = questionService.find(questionId);
        quizService.updateQuestion(quizDTO, questionNo, questionDTO);

    }


}
