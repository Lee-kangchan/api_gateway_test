package com.example.apt_gateway_test.service

import com.example.apt_gateway_test.dto.TokenDecrypt
import com.example.apt_gateway_test.entity.Dosa
import com.example.apt_gateway_test.entity.Users
import com.example.apt_gateway_test.filter.TokenCheckFilter
import com.example.apt_gateway_test.repository.DosaRepository
import com.example.apt_gateway_test.repository.DosaTokenRepository
import com.example.apt_gateway_test.repository.TokenRepository
import com.example.apt_gateway_test.repository.UserRepository
import com.example.apt_gateway_test.util.TokenUtil
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TokenService(
    val userRepository: UserRepository,
    val dosaRepository: DosaRepository,
    val tokenRepository: TokenRepository,
    val dosaTokenRepository: DosaTokenRepository
) {
    companion object  {

        val log = LoggerFactory.getLogger(TokenService::class.java) ?: throw IllegalStateException("log가 존재하지 않습니다!")
    }
    fun tokenDecrypt(token: String) : TokenDecrypt?{
        val dosaToken = tokenRepository.findAllByToken(token)
        val userToken = dosaTokenRepository.findAllByToken(token)
        if(dosaToken == null && userToken == null){
            return null;
        }
        val tokenDec : String = TokenUtil.decryptAES_DB(token)
        val tokenArr = tokenDec.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (tokenArr == null) return TokenDecrypt("", "", "")
        return TokenDecrypt(tokenArr[0], tokenArr[1], tokenArr[2])
    }

    fun userTokenCheck(token: TokenDecrypt) : Users?{
        val user : Users? = userRepository.findAllByUserId(token.tokenId);

        if (user == null || user.status == "delete" || user.status == "dormant") {
            return null;
        }
        return user;
    }

    fun dosaTokenCheck(token: TokenDecrypt): Dosa? {

        val dosa: Dosa? = dosaRepository.findAllByDosaId(token.tokenId)
        log.info(dosa.toString())
        if (dosa == null || dosa.status == "delete") {
            return null;
        }

        return dosa
    }
}