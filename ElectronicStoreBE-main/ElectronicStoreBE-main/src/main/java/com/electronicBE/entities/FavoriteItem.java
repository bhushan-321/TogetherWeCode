package com.electronicBE.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "favorite_items")
@Data

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteItem {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)  // Use LAZY fetching for product
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "favorite_id")
    private Favorite favorite;


    @Override
    public int hashCode() {
        return Objects.hash(id, product);  // Exclude 'favorite'
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FavoriteItem other = (FavoriteItem) obj;
        return Objects.equals(id, other.id) &&
                Objects.equals(product, other.product);
    }

}
