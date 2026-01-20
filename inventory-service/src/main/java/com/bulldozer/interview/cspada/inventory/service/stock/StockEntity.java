package com.bulldozer.interview.cspada.inventory.service.stock;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stock")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockEntity {

    @Id
    @Column(name = "alcohol")
    @Enumerated(EnumType.STRING)
    private Alcohol alcohol;

    @Column(name = "available_count")
    private int availableCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockEntity that)) {
            return false;
        }
        return alcohol != null && alcohol.equals(that.alcohol);
    }

    @Override
    public int hashCode() {
        // Use a constant hash if ID is null, otherwise use ID's hash
        return (alcohol == null ? 0 : alcohol.hashCode());
    }
}
