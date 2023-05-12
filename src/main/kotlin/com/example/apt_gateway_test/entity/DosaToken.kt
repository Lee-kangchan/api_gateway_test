package com.example.apt_gateway_test.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class DosaToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seq : Long,
    val dosaSeq : Long,
    val token : String,
    val tokenStart : String,
    val tokenExpired: String
)
