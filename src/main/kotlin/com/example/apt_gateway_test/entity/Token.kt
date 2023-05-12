package com.example.apt_gateway_test.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Token(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seq : Long,
    val usersSeq : Long,
    val token : String,
    val tokenStart : String,
    val tokenExpired: String
)
