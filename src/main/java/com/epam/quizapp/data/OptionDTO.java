package com.epam.quizapp.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OptionDTO {
    int id;
    String value;
    boolean isAnswer;



}
