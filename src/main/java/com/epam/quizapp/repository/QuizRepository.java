package com.epam.quizapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.epam.quizapp.data.Quiz;

public interface QuizRepository extends CrudRepository<Quiz, Integer> {

}
