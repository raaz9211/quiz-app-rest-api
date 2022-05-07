package com.epam.quizapp.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;
import java.util.List;
@Getter
@Setter
@ToString
public class QuizDTO {
    int id;
    @Size(min = 3, max = 20, message = "Title should be between 3 and 20 characters length")
    String name;
    List<QuestionDTO> questions;

}
