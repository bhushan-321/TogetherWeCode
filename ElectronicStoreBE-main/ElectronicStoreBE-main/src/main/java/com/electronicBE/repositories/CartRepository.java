package com.electronicBE.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronicBE.entities.Cart;
import com.electronicBE.entities.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	
 Optional<Cart> findByUser(User user);

}
