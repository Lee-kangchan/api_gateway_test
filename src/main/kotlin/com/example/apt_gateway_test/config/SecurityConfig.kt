package com.example.apt_gateway_test.config

import com.example.apt_gateway_test.entity.Dosa
import com.example.apt_gateway_test.entity.Users
import com.example.apt_gateway_test.filter.TokenCheckFilter
import com.example.apt_gateway_test.service.TokenService
import com.example.apt_gateway_test.util.CommonData.Companion.DOSA_ROLE
import com.example.apt_gateway_test.util.CommonData.Companion.USER_ROLE
import com.example.apt_gateway_test.util.CommonUtil
import org.slf4j.LoggerFactory
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
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
class SecurityConfig(
    val tokenService: TokenService
): WebSecurityConfigurerAdapter() {
    companion object {
        val log = LoggerFactory.getLogger(SecurityConfig::class.java) ?: throw IllegalStateException("log가 존재하지 않습니다!")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()

//            .exceptionHandling()
//            .authenticationEntryPoint()
//            .accessDeniedHandler()
//            .and()
            .authorizeRequests()
            .antMatchers("/dosa/**").hasAuthority("dosa")
            .antMatchers("/user/**").hasAuthority("user")
            .antMatchers("**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(TokenCheckFilter(tokenService), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(TokenAuthentication(), UsernamePasswordAuthenticationFilter::class.java)
    }
}

private class TokenAuthentication() : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val user: Users? = CommonUtil.getAttrFromRequest(USER_ROLE) as Users?
        val dosa: Dosa? = CommonUtil.getAttrFromRequest(DOSA_ROLE) as Dosa?

        val grantedList = ArrayList<SimpleGrantedAuthority>().apply {
            user?.let { add(SimpleGrantedAuthority(USER_ROLE)) }
            dosa?.let { add(SimpleGrantedAuthority(DOSA_ROLE)) }
        }

        try {
            val authentication: Authentication = UsernamePasswordAuthenticationToken(null, null, grantedList)

            SecurityContextHolder.getContext().authentication = authentication
        } catch (e: Exception) {
            SecurityContextHolder.clearContext()
        }

        filterChain.doFilter(request, response)
    }
}