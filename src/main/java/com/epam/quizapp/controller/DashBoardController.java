package com.epam.quizapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashBoardController {

    @RequestMapping("dashBoard")
    public String dashBoard() {
        return "dashBoard";
    }


    @RequestMapping("createQuestion")
    public String createQuestion() {
        return "createQuestion";
    }

    @RequestMapping("question")
    public String question() {
        return "question";
    }

    @RequestMapping("quiz")
    public String quiz() {
        return "quiz";
    }

    @RequestMapping("createQuiz")
    public String createQuiz() {
        return "createQuiz";
    }


}
