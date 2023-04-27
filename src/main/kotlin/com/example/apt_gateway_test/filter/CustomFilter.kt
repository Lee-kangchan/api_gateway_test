package com.example.apt_gateway_test.filter

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class CustomFilter : GatewayFilter {
    companion object {
        val log = LoggerFactory.getLogger(CustomFilter::class.java) ?: throw IllegalStateException("log가 존재하지 않습니다!")
    }
    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request = exchange.request
        val response = exchange.response

        // 요청과 응답 처리

        log.info("Custom Pre Filter: request id -> {}", request.id)

        // Custom Post Filter

        // Custom Post Filter
        return chain.filter(exchange).then(Mono.fromRunnable {
            log.info(
                "Custom POST filter : response id -> {}",
                response.statusCode
            )
        })
    }
}