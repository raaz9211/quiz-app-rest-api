package com.epam.quizapp.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExceptionResponse {

    String timestamp;
    String error;
    String status;
    String path;
}

