package com.electronicBE.services;

import com.electronicBE.dtos.FavoriteDto;
import com.electronicBE.entities.Product;
import com.electronicBE.entities.User;

public interface FavoriteService {


    FavoriteDto addToFavorite(Long productId , Long  userId);

    FavoriteDto getFavoriteOfUser(Long userId);

  void  removeProductFromFavorite(Long productId,Long userId);

}
