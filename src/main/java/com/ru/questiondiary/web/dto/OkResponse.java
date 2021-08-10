package com.ru.questiondiary.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OkResponse {
    private final Boolean status = true;
    private Object data;

//    public OkResponse(ResponseData data) {
//        this.data = data;
//    }

    public OkResponse(Object data) {
        this.data = data;
    }
}
