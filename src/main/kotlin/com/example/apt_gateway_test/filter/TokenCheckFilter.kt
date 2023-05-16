package com.example.apt_gateway_test.filter

import com.example.apt_gateway_test.dto.TokenDecrypt
import com.example.apt_gateway_test.entity.Dosa
import com.example.apt_gateway_test.entity.Users
import com.example.apt_gateway_test.service.TokenService
import com.example.apt_gateway_test.util.CommonData.Companion.DOSA_ROLE
import com.example.apt_gateway_test.util.CommonData.Companion.TOKEN
import com.example.apt_gateway_test.util.CommonData.Companion.USER_ROLE
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class TokenCheckFilter(
    val tokenService: TokenService
) : OncePerRequestFilter(){
    companion object  {

        val log = LoggerFactory.getLogger(TokenCheckFilter::class.java) ?: throw IllegalStateException("log가 존재하지 않습니다!")
    }
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

            val tokenDecrypt : TokenDecrypt = tokenService.tokenDecrypt(request.getHeader(TOKEN)) ?: TokenDecrypt();

            if (tokenDecrypt.tokenType == USER_ROLE) {
                val tokenUser : Users? = tokenService.userTokenCheck(tokenDecrypt);
                // TODO Token 유효기간 재설정하는 부분 추가해야함
//                val parameters: Map<String, Any> = expandToken(userVo.getSeq(), token)
//                loginMapper.insertToken(parameters)
//                loginMapper.updateLastLoginDate(userVo.getSeq())
//                userVo.setMobile(CommonUtil.decryptAES_DB(userVo.getMobile()))
                tokenUser.let { request.setAttribute(USER_ROLE, tokenUser)  }
                true
            } else if (tokenDecrypt.tokenType == DOSA_ROLE) {
                val tokenDosa : Dosa? = tokenService.dosaTokenCheck(tokenDecrypt);

                tokenDosa.let { request.setAttribute(DOSA_ROLE, tokenDosa) }
                // TODO Token 유효기간 재설정하는 부분 추가해야함

            }
        filterChain.doFilter(request, response)
    }
}
