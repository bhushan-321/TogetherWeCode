package com.electronicBE.controllers;

import com.electronicBE.dtos.ApiResponseMessage;
import com.electronicBE.dtos.FileResponse;
import com.electronicBE.dtos.PageableResponse;
import com.electronicBE.dtos.ProductDto;

import com.electronicBE.services.FileSerivce;
import com.electronicBE.services.ProductService;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileSerivce fileSerivce;

    @Value("${product.image.path}")
    private String productImagePath;

    // create
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<ProductDto> CreateProduct(@RequestBody ProductDto productDto) {

        ProductDto createProduct = this.productService.createProduct(productDto);


        return new ResponseEntity<ProductDto>(createProduct, HttpStatus.CREATED);

    }


    // update
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,
                                                   @PathVariable("productId") Long productId) {

        ProductDto updateProduct = this.productService.updateProduct(productDto, productId);

        


        return new ResponseEntity<ProductDto>(updateProduct, HttpStatus.OK);
    }

    // get all product
    @GetMapping("/")

    public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNUmber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PageableResponse<ProductDto> getAllProduct = this.productService.GetAllProduct(pageNUmber, pageSize, sortBy,
                sortDir);

        return new ResponseEntity(getAllProduct, HttpStatus.OK);

    }

    // get product by id

        @GetMapping("/single/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") Long productId) {

        ProductDto product = this.productService.getById(productId);


        return new ResponseEntity<ProductDto>(product, HttpStatus.OK);
    }

    // Search product

    @GetMapping("/{keyword}")
    public ResponseEntity<PageableResponse<ProductDto>> SearchProduct(@PathVariable("keyword") String keyword,

                                                          @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                          @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
                                                          @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                          @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    ) {

        PageableResponse<ProductDto> productDtoPageableResponse = this.productService.SearchProduct(pageNumber, pageSize, sortBy, sortDir, keyword);


        return  new ResponseEntity<>(productDtoPageableResponse,HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    // delete product
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable("productId") Long productId) throws NoSuchFileException {

        this.productService.deleteProduct(productId);

        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Product successfully deleted").httpStatus(HttpStatus.OK).success(true).build();

        return new ResponseEntity<ApiResponseMessage>(responseMessage, HttpStatus.OK);

    }


    // upload productImage
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/image/{productId}")
    public ResponseEntity<FileResponse> uploadImage(@RequestParam("file") MultipartFile file, @PathVariable("productId") Long productId) throws IOException {


        String uploadImage = this.fileSerivce.uploadImage(file, productImagePath);

        ProductDto product = this.productService.getById(productId);

        product.setProductImage(uploadImage);
        ProductDto updateProduct = this.productService.updateProduct(product, productId);

        FileResponse Response = FileResponse.builder().Filename(updateProduct.getProductImage()).message("Upload image successfully").httpStatus(HttpStatus.OK).build();
        return new ResponseEntity<FileResponse>(Response, HttpStatus.OK);

    }


    // Add product into category
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/category/{categoryId}")
    public ResponseEntity<ProductDto> AddProductIntoCategory(@RequestBody ProductDto productDto, @PathVariable("categoryId") Long categoryId) {

        ProductDto addProductIntoCategory = this.productService.AddProductIntoCategory(productDto, categoryId);

        return new ResponseEntity<ProductDto>(addProductIntoCategory, HttpStatus.OK);
    }

    // Serve Image
    @GetMapping(value="/file/{productId}")
    public void GetImage(@PathVariable("productId") Long productId, HttpServletResponse response) throws IOException {

        ProductDto Product = this.productService.getById(productId);

        InputStream resource = this.fileSerivce.getResource(productImagePath, Product.getProductImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());


    }

    // Get All Active product

    @GetMapping("/live/")
    public ResponseEntity<PageableResponse<ProductDto>> GetAllLiveProduct(


            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNUmber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

    ) {



        PageableResponse<ProductDto> getAllLiveProducts = this.productService.GetAllLiveProducts(pageNUmber, pageSize, sortBy, sortDir);

        return new ResponseEntity<PageableResponse<ProductDto>>(getAllLiveProducts, HttpStatus.OK);
    }

    // Get all product from category
    @GetMapping("/Live/category/{categoryId}")
    public ResponseEntity<PageableResponse<ProductDto>> GetAllLiveProductFromCategory(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNUmber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
            @PathVariable(value = "categoryId") Long categoryId
    ) {

        PageableResponse<ProductDto> getAllLiveProductFromCategory = this.productService.GetAllLiveProductFromCategory(pageNUmber, pageSize, sortBy, sortDir, categoryId);

        return new ResponseEntity<PageableResponse<ProductDto>>(getAllLiveProductFromCategory, HttpStatus.OK);

    }

}
