package br.com.rtarcisio.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

	@Bean
	public RouteLocator routes (RouteLocatorBuilder builder) {
		return builder
				.routes()
					.route(r -> r.path("/cambio-service/**")
							.uri("lb://cambio-service"))
					.route(r -> r.path("/book-service/**")
							.uri("lb://book-service"))
				.build();
	}
}
