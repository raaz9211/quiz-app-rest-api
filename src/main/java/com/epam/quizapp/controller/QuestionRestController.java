package com.epam.quizapp.controller;

import com.epam.quizapp.data.QuestionDTO;
import com.epam.quizapp.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController

public class QuestionRestController {

    @Autowired
    QuestionService questionService;

    @Operation(description = "Get all questions")
    @ApiResponse(responseCode = "202",description = "Successfully fetched")
    @GetMapping("questions")
    public ResponseEntity<List<QuestionDTO>> getQuestions() {
        return new ResponseEntity<>(questionService.getQuestion(), HttpStatus.ACCEPTED);
    }

    @Operation(description = "Create questions")
    @ApiResponse(responseCode = "201",description = "Successfully created")
    @ApiResponse(responseCode = "400",description = "Not created")
    @PostMapping("questions")
    public ResponseEntity<QuestionDTO> addQuestion(@RequestBody @Valid QuestionDTO questionDTO) {

        return new ResponseEntity<>(questionService.addQuestion(questionDTO), HttpStatus.CREATED);
    }


    @Operation(description = "Delete questions")
    @ApiResponse(responseCode = "204",description = "Successfully deleted")
    @ApiResponse(responseCode = "400",description = "Not deleted")
    @DeleteMapping("questions/{id}")
    public ResponseEntity<Integer> deleteQuestion(@PathVariable("id") int id) {
        QuestionDTO questionDTO = questionService.find(id);
        questionService.removeQuestion(questionDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(description = "Update question")
    @ApiResponse(responseCode = "201",description = "Successfully updated")
    @ApiResponse(responseCode = "400",description = "Not updated")
    @PutMapping("questions/{id}")
    public ResponseEntity<QuestionDTO> editQuestion(@PathVariable("id") int id, @RequestBody @Valid QuestionDTO questionDTO) {

        QuestionDTO foundQuestion = questionService.find(id);
        questionService.updateQuestion(questionDTO,foundQuestion);
        return new ResponseEntity<>(questionDTO, HttpStatus.CREATED);


    }
}
