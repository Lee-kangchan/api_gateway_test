package com.example.apt_gateway_test.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Dosa(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seq: Long?,
    val dosaId: String?,
    val pw: String?,
    val pwUpdated: String?,
    val nm: String?,
    val nickname: String?,
    val mobile: String?,
    val dosaType: String?,
    val lastLoginDt: String?,
    val status: String?,
    val updated: String?,
    val created: String?,
    val coNm: String?,
    val coNumber: String?,
    val bankCd: String?,
    val bankUsername: String?,
    val bankAccount: String?,
    val introduceTitle: String?,
    val introduceContent: String?,
    val detailMsg: String?,
    val reserveMsg: String?,
    val gender: String?,
    val senseScore: Double?,
    val recommendCnt: Int?,
    val returnScore: Double?,
    val famousScore: Double?,
    val reviewCnt: Int?,
    val cnslCnt: Int?,
    val reserveStatus: String?,
    val privacyAgreeYn: String?,
    val privacyAgreeDt: String?,
    val registrationNum: String?,
    val contract: String?,
    val email: String?,
    val tabNo: String?,
    val shareCnt: Int?,
    val freeCnslYn: String?,
    val introduceVideo: String?,
    val settlementRate: String?,
    val smartStoreLink: String?,
    val deferredYn: String?,
    val deferredNumber: String?,
    val deferredCode: String?
)
