package com.bulldozer.interview.cspada.bar.service.comsumption;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "beer_consumption")
public class BeerConsumptionEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "consumed_at")
    private LocalDateTime consumedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeerConsumptionEntity that)) {
            return false;
        }
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        // Use a constant hash if ID is null, otherwise use ID's hash
        return (id == null ? 0 : id.hashCode());
    }
}
