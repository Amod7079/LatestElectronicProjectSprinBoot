package com.CoderXAmod.Electronic.Services;

import com.CoderXAmod.Electronic.dtos.CategoriesDto;
import com.CoderXAmod.Electronic.dtos.PageableResponce;

public interface CategorryService {
CategoriesDto create(CategoriesDto categoriesDto);




CategoriesDto update(CategoriesDto categoriesDto, String categoryId);

void delete(String categoryId);


PageableResponce<CategoriesDto> getAll(int pageNumber , int pageSize,String sortBy,String sortDir);


CategoriesDto getSingleCategory(String categoryId);
}
