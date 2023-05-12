package com.example.apt_gateway_test.repository

import com.example.apt_gateway_test.entity.DosaToken
import org.springframework.data.jpa.repository.JpaRepository

interface DosaTokenRepository : JpaRepository<DosaToken, Long> {
    fun findAllByToken(token: String?) : DosaToken
}