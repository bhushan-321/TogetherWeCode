package com.electronicBE.entities;



import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long categoryId;

    private String title;

    private String description;

    private String coverImage;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "category",fetch = FetchType.LAZY)
    private List<Product>products= new ArrayList<>();




}
