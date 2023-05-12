package com.example.apt_gateway_test.dto

data class TokenDecrypt(
    val tokenId : String?,
    val tokenStart : String?,
    val tokenType : String?
){
    constructor() : this("","","")
}
