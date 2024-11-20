package com.electronicBE.services.impl;

import com.electronicBE.dtos.PageableResponse;
import com.electronicBE.dtos.ProductDto;
import com.electronicBE.entities.Category;
import com.electronicBE.entities.Product;
import com.electronicBE.exceptions.ResourceNotFoundException;
import com.electronicBE.helper.Helper;
import com.electronicBE.repositories.CategoryRepo;
import com.electronicBE.repositories.ProductRepo;

import com.electronicBE.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepo categoryRepo;

    @Value("${product.image.path}")
    private String imagePath;


    @Override
    public ProductDto createProduct(ProductDto productDto) {

        Product product = modelMapper.map(productDto, Product.class);

        String date = new SimpleDateFormat("dd/MM/YYYY, hh.mm.ss.aa").format(Calendar.getInstance().getTime());

        product.setAddedDate(date);


        Product savedProduct = productRepo.save(product);

        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long productId) {

        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with given id"));


        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setProductImage(productDto.getProductImage());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setAddedDate(productDto.getAddedDate());
        product.setInStocked(productDto.getInStocked());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.getLive());
        Product updatedProduct = productRepo.save(product);


        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public ProductDto getById(Long productId) {

        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with given id"));


        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> GetAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> page = productRepo.findAll(p);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);

        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> SearchProduct(int pageNumber, int pageSize, String sortBy, String sortDir, String keywords) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> list = productRepo.findByTitleContaining(pageable, keywords);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(list, ProductDto.class);

//        List<ProductDto> productDtos = list.stream().map((list1) -> modelMapper.map(list1, ProductDto.class)).collect(Collectors.toList());


        return pageableResponse;
    }

    @Override
    public void deleteProduct(Long productId) throws NoSuchFileException {

        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with given id"));


        String fullPath = imagePath + product.getProductImage();

        Path path = Paths.get(fullPath);

        try {
            Files.delete(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        productRepo.delete(product);

    }

    @Override
    public PageableResponse<ProductDto> GetAllProductFromCategory(int pageNumber, int pageSize, String sortBy, String sortDir,
                                                                  Long categoryId) {
        // TODO Auto-generated method stub

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(" Category not found with given id"));

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> page = this.productRepo.findByCategory(category, pageable);


        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public ProductDto AddProductIntoCategory(ProductDto productDto, Long categoryId) {
        // TODO Auto-generated method stub

        Category Category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id"));

        Product Product = modelMapper.map(productDto, Product.class);

//		String date= new SimpleDateFormat("dd/MM/YYYY, hh.mm.ss.aa").format(Calendar.getInstance().getTime());
//
//		Product.setAddedDate(date);

        Product.setCategory(Category);
        Product SavedProduct = this.productRepo.save(Product);

        return modelMapper.map(SavedProduct, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> GetAllLiveProducts(int pageNumber, int pageSize, String sortBy, String sortDir
    ) {
        // TODO Auto-generated method stub

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> Page = this.productRepo.findByLive(pageable, true);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(Page, ProductDto.class);

        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> GetAllLiveProductFromCategory(int pageNumber, int pageSize, String sortBy,
                                                                      String sortDir, Long categoryId) {
        // TODO Auto-generated method stub


        Category Category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with given id"));

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy)).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> product = this.productRepo.findByLiveAndCategory(pageable, true, Category);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(product, ProductDto.class);


        return pageableResponse;
    }


}
