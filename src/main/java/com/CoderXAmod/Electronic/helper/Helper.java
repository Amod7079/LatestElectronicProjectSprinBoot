package com.CoderXAmod.Electronic.helper;

import com.CoderXAmod.Electronic.dtos.PageableResponce;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {
    public static <U,V> PageableResponce<V> getPageableResponse(Page<U> page ,Class<V> vClass)
    {
        List<U> entity = page.getContent();
        List<V> dtoList = entity.stream().map(object ->new ModelMapper().map(object,vClass)).collect(Collectors.toList());
        PageableResponce<V> pageableResponce = new PageableResponce();
        pageableResponce.setContent(dtoList);
        pageableResponce.setPageNumber(page.getNumber());
        pageableResponce.setPageSize(page.getSize());
        pageableResponce.setLastPage(page.isLast());
        pageableResponce.setTotalPages(page.getTotalPages());
        pageableResponce.setTototalElement(page.getTotalElements());
        return  pageableResponce;
    }
}
