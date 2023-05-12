package com.example.apt_gateway_test.repository

import com.example.apt_gateway_test.entity.Dosa
import org.springframework.data.jpa.repository.JpaRepository

interface DosaRepository : JpaRepository<Dosa, Long>{
    fun findAllByDosaId(dosaId: String?): Dosa?
}