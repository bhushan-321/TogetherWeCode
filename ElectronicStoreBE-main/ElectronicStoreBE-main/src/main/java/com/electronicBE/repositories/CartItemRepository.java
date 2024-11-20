package com.electronicBE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronicBE.entities.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{

}
