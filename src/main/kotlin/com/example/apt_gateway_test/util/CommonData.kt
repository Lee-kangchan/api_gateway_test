package com.example.apt_gateway_test.util

import com.example.apt_gateway_test.filter.TokenCheckFilter
import org.slf4j.LoggerFactory

class CommonData {
    companion object  {
        const val TOKEN: String =  "token";
        const val USER_ROLE: String = "user";
        const val DOSA_ROLE: String = "dosa";
        const val USER_STATUS_DELETE: String = "delete";
        val log = LoggerFactory.getLogger(TokenCheckFilter::class.java) ?: throw IllegalStateException("log가 존재하지 않습니다!")
    }
}