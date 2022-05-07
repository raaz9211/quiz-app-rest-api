package com.epam.quizapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.epam.quizapp.data.Question;

public interface QuestionRepository extends CrudRepository<Question, Integer> {

}
