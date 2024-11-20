package com.electronicBE.repositories;

import com.electronicBE.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {

        Role findByroleName(String roleName);
}
