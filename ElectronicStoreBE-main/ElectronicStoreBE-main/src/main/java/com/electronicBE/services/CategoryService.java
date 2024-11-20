package com.electronicBE.services;

import com.electronicBE.dtos.CategoryDto;
import com.electronicBE.dtos.PageableResponse;

import java.util.List;

public interface CategoryService {

    // Create

    CategoryDto create(CategoryDto categoryDto);

    //update

    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);

    // get by id

    CategoryDto getById( Long categoryId);

    // get all

    PageableResponse<CategoryDto> GetAllCategory(int pageNumber,int pageSize, String sortBy,String sortDir);

    // search

    List<CategoryDto> SearchCategory(String keyword);

    // delete

    void deleteById(Long categoryId);

}
