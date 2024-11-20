package com.electronicBE.repositories;

import com.electronicBE.entities.Cart;
import com.electronicBE.entities.Favorite;
import com.electronicBE.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.social.facebook.api.Facebook;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite,Long> {

    Optional<Favorite> findByUser(User user);
}
