package com.CoderXAmod.Electronic.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesDto {
    private  String categoryId;
    private String title;
    public String description ;
    public String coverImage;

}
