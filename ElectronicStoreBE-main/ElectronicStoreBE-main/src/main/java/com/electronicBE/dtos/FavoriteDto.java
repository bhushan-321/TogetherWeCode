package com.electronicBE.dtos;

import com.electronicBE.entities.FavoriteItem;
import lombok.*;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDto {



    private Long id;


    private UserDto user;

    private Date createdDate;


    private Set<FavoriteItemDto> items = new HashSet<>();


}
