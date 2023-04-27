package com.example.apt_gateway_test

import com.example.apt_gateway_test.filter.CustomFilter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableDiscoveryClient
class AptGatewayTestApplication

fun main(args: Array<String>) {
    runApplication<AptGatewayTestApplication>(*args)
}

@Bean
fun myRoutes(builder: RouteLocatorBuilder): RouteLocator? {
    return builder.routes()
        .route("user") { r ->
            r.path("/user/**")
                .filters { f ->
                    f.removeRequestHeader("Cookie")
                        .rewritePath("/user/(?<segment>.*)", "/\${segment}")
                        .filter(CustomFilter())
                }
                .uri("lb://USER-SERVICE")
        }
        .build()
}