package com.electronicBE.dtos;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableResponse<T> {

    private List<T> content;
    private int pageNumber;

    private int pageSize;

    private int totalPages;

    private Long totalElements;

    private Boolean LastPage;

}
