package com.bulldozer.interview.cspada.bar.service;

import com.bulldozer.interview.cspada.bar.service.comsumption.BeerConsumptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BarController {

    private final BeerConsumptionService beerConsumptionService;

    @PostMapping("/bar/consumption")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public void consumeBeer(@RequestParam("user_id") String userId, JwtAuthenticationToken auth) {
        beerConsumptionService.consumeBeer(userId);
    }
}
