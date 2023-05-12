package com.example.apt_gateway_test.filter

import com.example.apt_gateway_test.dto.TokenDecrypt
import com.example.apt_gateway_test.entity.Dosa
import com.example.apt_gateway_test.entity.Users
import com.example.apt_gateway_test.service.TokenService
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

            val tokenDecrypt : TokenDecrypt = tokenService.tokenDecrypt(request.getHeader("token")) ?: TokenDecrypt();

            log.info("token check")
            log.info(tokenDecrypt.toString())
            if (tokenDecrypt.tokenType == "user") {
                val tokenUser : Users? = tokenService.userTokenCheck(tokenDecrypt);
                log.info("user token check")
                // TODO Token 유효기간 재설정하는 부분 추가해야함
//                val parameters: Map<String, Any> = expandToken(userVo.getSeq(), token)
//                loginMapper.insertToken(parameters)
//                loginMapper.updateLastLoginDate(userVo.getSeq())
//                userVo.setMobile(CommonUtil.decryptAES_DB(userVo.getMobile()))
                if(tokenUser == null ){
                    filterChain.doFilter(request, response)
                }
                request.setAttribute("user", tokenUser)
                true
            } else if (tokenDecrypt.tokenType == "dosa") {

                val tokenDosa : Dosa? = tokenService.dosaTokenCheck(tokenDecrypt);
                log.info("dosa token check : " + tokenDecrypt)

                if(tokenDosa == null) {
                    filterChain.doFilter(request, response)
                }
                // TODO Token 유효기간 재설정하는 부분 추가해야함

                request.setAttribute("dosa", tokenDosa)
            }
        filterChain.doFilter(request, response)
    }
}
