package com.epam.quizapp.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
@Setter
@Getter
@ToString
public class QuestionDTO {
    int id;

    @Size(min = 5, max = 20, message = "Title should be between 5 and 20 characters length")
    String title;

    @NotEmpty(message = "difficulty should not be empty")
    String difficulty;
    String tag;
    List<OptionDTO> options;

}
