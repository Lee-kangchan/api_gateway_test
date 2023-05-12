package com.example.apt_gateway_test.repository

import com.example.apt_gateway_test.entity.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<Users, Long>{
    fun findAllByUserId(userId: String? ): Users?

}