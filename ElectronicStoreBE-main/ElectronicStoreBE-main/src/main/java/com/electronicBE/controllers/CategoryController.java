package com.electronicBE.controllers;


import com.electronicBE.dtos.ApiResponseMessage;
import com.electronicBE.dtos.CategoryDto;
import com.electronicBE.dtos.PageableResponse;
import com.electronicBE.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

      // create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {

        CategoryDto SavedCategory = this.categoryService.create(categoryDto);

        return new ResponseEntity<>(SavedCategory, HttpStatus.CREATED);

    }

    // update

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Long categoryId){

        CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, categoryId);

        return   new ResponseEntity<>(updateCategory,HttpStatus.OK);
    }

    // get single category

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Long categoryId){

        CategoryDto category = this.categoryService.getById(categoryId);

        return  new ResponseEntity<>(category,HttpStatus.OK);
    }

    // list of categories

    @GetMapping("/")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "title",required = false)String sortBy,
            @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir
    ){

        PageableResponse<CategoryDto> categoryDtoPageableResponse = categoryService.GetAllCategory(pageNumber, pageSize, sortBy, sortDir);

        return  new ResponseEntity<>(categoryDtoPageableResponse,HttpStatus.OK);
    }

    // search category


    @GetMapping("/Search/{keywords}")
    public ResponseEntity<List<CategoryDto>> SearchCategory(@PathVariable String keywords){

        List<CategoryDto> categoryDtos = categoryService.SearchCategory(keywords);

        return new ResponseEntity<>(categoryDtos,HttpStatus.OK);
    }

    // delete category

    @DeleteMapping("/delete/{categoryId}")
    public  ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable Long categoryId){


        categoryService.deleteById(categoryId);

        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Delete category successfully").success(true).httpStatus(HttpStatus.OK).build();



        return  new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }





}
