package com.electronicBE.services.impl;


import com.electronicBE.dtos.FavoriteDto;
import com.electronicBE.entities.Favorite;
import com.electronicBE.entities.FavoriteItem;
import com.electronicBE.entities.Product;
import com.electronicBE.entities.User;
import com.electronicBE.exceptions.BadApiException;
import com.electronicBE.exceptions.ResourceNotFoundException;
import com.electronicBE.repositories.FavoriteItemRepository;
import com.electronicBE.repositories.FavoriteRepository;
import com.electronicBE.repositories.ProductRepo;
import com.electronicBE.repositories.UserRepository;
import com.electronicBE.services.FavoriteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class FavoriteServiceImpl implements FavoriteService {



    @Autowired
    private FavoriteRepository favoriteRepository;


    @Autowired
    private FavoriteItemRepository favoriteItemRepository;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;


    @Override
//    public FavoriteDto addToFavorite(Long productId, Long userId) {
//
//
//        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product is  not present with given id"));
//
//        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
//
//        Favorite favorite = null;
//
//        try {
//            favorite = this.favoriteRepository.findByUser(user).get();
//        } catch (NoSuchElementException e) {
//            favorite = new Favorite();
//            favorite.setCreatedDate(new Date());
//        }
//
//
//
//        // if favorite already present
//
//        boolean productAlreadyFavorited = favorite.getItems().stream().anyMatch((i) -> i.getProduct().getProductId().equals(productId));
//
//        if (productAlreadyFavorited) {
//            throw new BadApiException("Product already present in favorite");
//        }
//
//        FavoriteItem favoriteItem = new FavoriteItem().builder().favorite(favorite).product(product).build();
//
//
//
//        favorite.getItems().add(favoriteItem);
//        favorite.setUser(user);
//
//        Favorite savedFavorite = this.favoriteRepository.save(favorite);
//
//        System.out.println("After save Favorite "+savedFavorite);
//
//
//        FavoriteDto favoriteDto = this.mapper.map(savedFavorite, FavoriteDto.class);
//
//        return favoriteDto;
//    }

    public FavoriteDto addToFavorite(Long productId, Long userId) {

        Product product = this.productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product is not present with given id"));

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));

        Favorite favorite = this.favoriteRepository.findByUser(user).orElseGet(() -> {
            Favorite newFavorite = new Favorite();
            newFavorite.setCreatedDate(new Date());
            return newFavorite;
        });

        // Check if product is already favorited
        boolean productAlreadyFavorited = favorite.getItems().stream()
                .anyMatch(i -> i.getProduct().getProductId().equals(productId));

        if (productAlreadyFavorited) {
            throw new BadApiException("Product already present in favorite");
        }

        // Add favorite item
        FavoriteItem favoriteItem = new FavoriteItem();
        favoriteItem.setFavorite(favorite);
        favoriteItem.setProduct(product);

        favorite.getItems().add(favoriteItem);
        favorite.setUser(user);

        // Save favorite
        Favorite savedFavorite = this.favoriteRepository.save(favorite);

        // Map savedFavorite to FavoriteDto
        FavoriteDto favoriteDto = this.mapper.map(savedFavorite, FavoriteDto.class);

        return favoriteDto;
    }


    @Override
    public FavoriteDto getFavoriteOfUser(Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));

        Favorite favorite = this.favoriteRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Favorite not present with given user"));
        FavoriteDto favoriteDto = mapper.map(favorite, FavoriteDto.class);
        return favoriteDto;
    }

    @Override
    public void removeProductFromFavorite( Long productId,Long userId) {

        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with given id"));
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));


        Favorite favorite = this.favoriteRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Favorite not present with given user"));


        FavoriteItem favoriteItem = favorite.getItems().stream().filter((item) -> item.getProduct().getProductId().equals(product.getProductId())).findFirst().orElseThrow(() -> new ResourceNotFoundException("Product is not in the favorite list"));

        favorite.getItems().remove(favoriteItem);

        this.favoriteItemRepository.delete(favoriteItem);

        this.favoriteRepository.save(favorite);

    }
}
