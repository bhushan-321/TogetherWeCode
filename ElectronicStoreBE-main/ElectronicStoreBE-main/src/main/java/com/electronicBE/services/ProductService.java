package com.electronicBE.services;

import com.electronicBE.dtos.PageableResponse;
import com.electronicBE.dtos.ProductDto;
import com.electronicBE.entities.Category;

import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    // create


    ProductDto createProduct(ProductDto productDto);


    // update

    ProductDto updateProduct(ProductDto productDto,Long productId);

    // get by id

    ProductDto getById(Long productId);

    // Get All product

    PageableResponse<ProductDto> GetAllProduct(int pageNumber,int pageSize,String sortBy,String sortDir);

    // search product

    PageableResponse<ProductDto> SearchProduct(int pageNumber,int pageSize,String sortBy,String Dir,String keywords);

    // delete product

    void deleteProduct(Long productId) throws NoSuchFileException;
    
    
    // get List product from category
  
    PageableResponse<ProductDto>GetAllProductFromCategory(int pageNumber,int pageSize,String sortBy,String sortDir,Long categoryId);
    
    
    // add product into category
    
    ProductDto AddProductIntoCategory(ProductDto productDto , Long categoryId);
    

    // get All live product
    
    PageableResponse<ProductDto>GetAllLiveProducts(int pageNumber,int pageSize,String sortBy,String sortDir);
    
   // get All live product from category
    
  
    PageableResponse<ProductDto>GetAllLiveProductFromCategory(int pageNumber,int pageSize,String sortBy,String sortDir,Long categoryId);
    
}
