package com.example.apt_gateway_test.config

import com.example.apt_gateway_test.entity.Dosa
import com.example.apt_gateway_test.entity.User
import com.example.apt_gateway_test.util.TokenUtil
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.stream.Collectors
import javax.crypto.Cipher.SECRET_KEY
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
class SecurityConfig(): WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers("/dosa").hasAuthority("dosa")
            .antMatchers("/user").hasAuthority("user")
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(TokenAuthentication(), UsernamePasswordAuthenticationFilter::class.java)
    }

    private class TokenAuthentication() : OncePerRequestFilter() {

        @Throws(ServletException::class, IOException::class)
        override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
        ) {
            val token = request.getHeader("token")
            if (token != null) {

                val user : User = TokenUtil.getAttrFromRequest("userVo") as User
                val dosa : Dosa = TokenUtil.getAttrFromRequest("dosaVo") as Dosa

                val grantedList = ArrayList<SimpleGrantedAuthority>()
                try {
                    if (user != null) {
                        grantedList += SimpleGrantedAuthority("user");
                    } else if (dosa != null) {
                        grantedList += SimpleGrantedAuthority("dosa");
                    }

                    val authentication: Authentication =
                        UsernamePasswordAuthenticationToken(null, null, grantedList)

                    SecurityContextHolder.getContext().authentication = authentication
                } catch (e: java.lang.Exception) {
                    SecurityContextHolder.clearContext()
                }
            }
            filterChain.doFilter(request, response)
        }
    }
}