package com.electronicBE.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="favorites")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private Date createdDate;

    @OneToMany(mappedBy = "favorite",cascade = CascadeType.ALL,orphanRemoval = true ,fetch = FetchType.EAGER)
    private Set<FavoriteItem> items=new LinkedHashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id, createdDate);  // Exclude 'items'
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Favorite other = (Favorite) obj;
        return Objects.equals(id, other.id) &&
                Objects.equals(createdDate, other.createdDate);
    }

}
