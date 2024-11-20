package com.electronicBE.services.impl;

import com.electronicBE.dtos.CategoryDto;
import com.electronicBE.dtos.PageableResponse;
import com.electronicBE.entities.Category;
import com.electronicBE.exceptions.ResourceNotFoundException;
import com.electronicBE.helper.Helper;
import com.electronicBE.repositories.CategoryRepo;
import com.electronicBE.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {

        Category category = modelMapper.map(categoryDto, Category.class);

        Category saveCategory = categoryRepo.save(category);


        return modelMapper.map(saveCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id"));

        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());

        Category saveCategory = categoryRepo.save(category);

        return modelMapper.map(saveCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getById(Long categoryId) {

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id"));

        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> GetAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);


        Page<Category> page = categoryRepo.findAll(pageable);

        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);

        return pageableResponse;
    }

    @Override
    public List<CategoryDto> SearchCategory(String keyword) {

        List<Category> categories = categoryRepo.findByTitleContaining(keyword);

        List<CategoryDto> categoryDtos = categories.stream().map((category -> modelMapper.map(category, CategoryDto.class))).collect(Collectors.toList());


        return categoryDtos;
    }

    @Override
    public void deleteById(Long categoryId) {

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id"));

        categoryRepo.delete(category);



    }
}