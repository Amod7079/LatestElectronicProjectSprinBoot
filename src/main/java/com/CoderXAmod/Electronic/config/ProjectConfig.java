package com.CoderXAmod.Electronic.config;

import com.CoderXAmod.Electronic.Entities.Categories;
import com.CoderXAmod.Electronic.dtos.CategoriesDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ProjectConfig {
    @Bean
    public ModelMapper mapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<CategoriesDto, Categories> categoriesTypeMap = modelMapper.createTypeMap(CategoriesDto.class, Categories.class);
        return modelMapper;
    }
}
