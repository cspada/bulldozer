package com.bulldozer.interview.cspada.gohome.service.comsumption;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "latest_beer_consumption")
public class LatestBeerConsumptionEntity {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "consumed_at")
    private LocalDateTime consumedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LatestBeerConsumptionEntity that)) {
            return false;
        }
        return userId != null && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        // Use a constant hash if ID is null, otherwise use ID's hash
        return (userId == null ? 0 : userId.hashCode());
    }
}
