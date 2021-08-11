package com.ru.questiondiary.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ErrorDto {
    private String message;
    private ErrorCode code;
    private String date;
}
