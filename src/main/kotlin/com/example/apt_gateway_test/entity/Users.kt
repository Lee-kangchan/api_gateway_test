package com.example.apt_gateway_test.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Users(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seq: Long,
    val userId: String,
    val email: String,
    val nm: String,
    val nickname: String,
    val mobile: String,
    val gender: String,
    val status: String,
    val lastLoginDt: String,
    val updated: String,
    val created: String,
    val birthYear: String,
    val birthMonth: String,
    val birthDay: String,
    val birthTime: Int,
    val solunar: String,
    val youndalYn: String,
    val recommendId: String,
    val coin: Int = 0,
    val cashCoin: Int = 0,
    val rewardCoin: Int = 0,
    val appPushYn: String?,
    val countryCode: String?,
    val countryName: String?,
    val friendBenefit: Int?,
    val refreshToken: String?

) {
}