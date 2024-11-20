package com.electronicBE.controllers;


import com.electronicBE.dtos.ApiResponseMessage;
import com.electronicBE.dtos.FavoriteDto;
import com.electronicBE.entities.User;
import com.electronicBE.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {



    @Autowired
    private FavoriteService favoriteService;



    @PostMapping("/{productId}/{userId}")
    public ResponseEntity<FavoriteDto> addToFavorite(@PathVariable("productId")Long productId,@PathVariable("userId") Long userId){

        FavoriteDto favoriteDto = this.favoriteService.addToFavorite(productId, userId);

        return  new ResponseEntity<>(favoriteDto, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<FavoriteDto> getFavoriteOfUser(@PathVariable("userId") Long userId){

        FavoriteDto favoriteDto = this.favoriteService.getFavoriteOfUser(userId);

        return  new ResponseEntity<>(favoriteDto, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{productId}/{userId}")
    public ResponseEntity<ApiResponseMessage> removeItemFromFavorite(@PathVariable("productId")Long productId, @PathVariable("userId") Long userId){

     this.favoriteService.removeProductFromFavorite(productId,userId);

        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
                .httpStatus(HttpStatus.OK)
                .success(true)
                .message("Item remove successfully")
                .build();

        return  new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }





}
