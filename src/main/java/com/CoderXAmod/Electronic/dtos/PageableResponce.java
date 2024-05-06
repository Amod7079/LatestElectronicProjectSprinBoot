package com.CoderXAmod.Electronic.dtos;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableResponce<T> {
    private List<T> content;
    private  int pageNumber;
    private  int pageSize;
    private long tototalElement;
    private int totalPages;
    private boolean lastPage;
}
