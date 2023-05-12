package com.example.apt_gateway_test.filter

import com.example.apt_gateway_test.entity.Dosa
import com.example.apt_gateway_test.entity.DosaToken
import com.example.apt_gateway_test.entity.User
import com.example.apt_gateway_test.repository.DosaRepository
import com.example.apt_gateway_test.repository.DosaTokenRepository
import com.example.apt_gateway_test.repository.TokenRepository
import com.example.apt_gateway_test.repository.UserRepository
import com.example.apt_gateway_test.util.TokenUtil
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenCheckFilter(
    val userRepository: UserRepository,
    val dosaRepository: DosaRepository,
    val tokenRepository: TokenRepository,
    val dosaTokenRepository: DosaTokenRepository
) : OncePerRequestFilter(){
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        val token = request.getHeader("token")
        val tokenDec: String? = TokenUtil.decryptAES_DB(token)
        if(tokenDec != null) {

            val tokenArr = tokenDec.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val token_id = tokenArr[0]
            val tokenStart = tokenArr[1]
            val tokenType = tokenArr[2]

            if (tokenType === "user") {

                val user : User? = userRepository.findByUserId(token_id)
                if (user == null || user.seq === 0L) {
                    filterChain.doFilter(request, response)
                }
                else if (user.status == "delete" || user.status == "dormant") {
                    filterChain.doFilter(request, response)
                }

                // TODO Token 유효기간 재설정하는 부분 추가해야함

//                val parameters: Map<String, Any> = expandToken(userVo.getSeq(), token)
//                loginMapper.insertToken(parameters)
//                loginMapper.updateLastLoginDate(userVo.getSeq())
//                userVo.setMobile(CommonUtil.decryptAES_DB(userVo.getMobile()))
                request.setAttribute("userVo", user)
                true
            } else if (tokenType === "dosa") {
                val dosaToken: DosaToken? = dosaTokenRepository.findAllByToken(token)
                    if (dosaToken != null) {
                        filterChain.doFilter(request, response)
                    }
                }
                val dosa = dosaRepository.findAllByDosaId(token_id)
                if (dosa == null || dosa.status === "delete") {
                    filterChain.doFilter(request, response)
                }
                // TODO Token 유효기간 재설정하는 부분 추가해야함

//                val parameters: Map<String, Any> = expandTokenDosa(dosaVo.getSeq(), token)
//                loginMapper.insertDosaToken(parameters)
//                dosaVo.setMobile(CommonUtil.decryptAES_DB(dosaVo.getMobile()))
                request.setAttribute("dosa", dosa)
                true
            }

        filterChain.doFilter(request, response)
    }



}