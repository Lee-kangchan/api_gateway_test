package com.example.apt_gateway_test.filter

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class GlobalFilter : GatewayFilterFactory<GlobalFilter.Config> {

    companion object {
        val log = LoggerFactory.getLogger(GlobalFilter::class.java) ?: throw IllegalStateException("log가 존재하지 않습니다!")
    }
    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request = exchange.request
            val response = exchange.response

            if (config.preLogger) {
                log.info("Global filter Start : request id -> {}", request.id)
            }

            chain.filter(exchange).then(Mono.fromRunnable {
                if (config.postLogger) {
                    log.info("Global filter End : response id -> {}", response.statusCode)
                }
            })
        }
    }

    override fun shortcutFieldOrder(): MutableList<String> {
        return mutableListOf("baseMessage", "preLogger", "postLogger")
    }

    data class Config(
        var baseMessage: String? = null,
        var preLogger: Boolean = true,
        var postLogger: Boolean = true
    )
}