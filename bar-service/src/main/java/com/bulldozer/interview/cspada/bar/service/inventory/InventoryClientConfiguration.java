package com.bulldozer.interview.cspada.bar.service.inventory;

import com.bulldozer.interview.cspada.bar.service.ApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class InventoryClientConfiguration {

    @Bean
    public InventoryClient inventoryClient(RestClient.Builder restClientBuilder,
                                           ApplicationProperties applicationProperties) {
        RestClient restClient = restClientBuilder
                .baseUrl(applicationProperties.getInventory().getBaseUrl())
                .requestInterceptor((request, body, execution) -> {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication instanceof JwtAuthenticationToken jwtAuth) {
                        request.getHeaders().setBearerAuth(jwtAuth.getToken().getTokenValue());
                    }
                    return execution.execute(request, body);
                })                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(InventoryClient.class);
    }
}
