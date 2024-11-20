package com.electronicBE.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronicBE.entities.Order;
import com.electronicBE.entities.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>{
	
	List<Order> findByUser(User user);

}
