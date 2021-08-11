package com.ru.questiondiary.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PaginationDto {
    private Boolean hasNextPage;
    private Integer nextPage;
    private Object content;

    public PaginationDto(Object content, Boolean hasNextPage, Integer nextPage) {
        this.hasNextPage = hasNextPage;
        if (hasNextPage) {
            this.nextPage = nextPage;
        }
        this.content = content;
    }
}
