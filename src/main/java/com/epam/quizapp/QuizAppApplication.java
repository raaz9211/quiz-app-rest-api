package com.epam.quizapp;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Quiz API", version = "1.0", description = "Let's Play......"))
public class QuizAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuizAppApplication.class);
    }
}
