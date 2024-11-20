package com.electronicBE.repositories;

import com.electronicBE.entities.FavoriteItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteItemRepository extends JpaRepository<FavoriteItem,Long> {
}
