package com.electronicBE.repositories;


import com.electronicBE.entities.Category;
import com.electronicBE.entities.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

Page<Product>findByCategory(Category category,Pageable pageable);

    Page<Product>findByTitleContaining(Pageable pageable,String keywords);
    
    Page<Product> findByLive(Pageable pageable,Boolean b);
    
    Page<Product>findByLiveAndCategory(Pageable pageable,Boolean b,Category category);
    
   

}
