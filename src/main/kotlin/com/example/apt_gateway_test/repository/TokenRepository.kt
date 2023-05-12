package com.example.apt_gateway_test.repository

import com.example.apt_gateway_test.entity.Token
import org.springframework.data.jpa.repository.JpaRepository

interface TokenRepository : JpaRepository<Token, Long> {
    fun findAllByToken(token : String?): Token
}