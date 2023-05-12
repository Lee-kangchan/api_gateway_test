package com.example.apt_gateway_test.repository

import com.example.apt_gateway_test.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>{
    fun findByUserId(userId: String? ): User?

}