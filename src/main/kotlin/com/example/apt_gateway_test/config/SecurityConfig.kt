package com.example.apt_gateway_test.config

import com.example.apt_gateway_test.entity.Dosa
import com.example.apt_gateway_test.entity.User
import com.example.apt_gateway_test.filter.CustomFilter
import com.example.apt_gateway_test.util.TokenUtil
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
class SecurityConfig(): WebSecurityConfigurerAdapter() {
    companion object {
        val log = LoggerFactory.getLogger(CustomFilter::class.java) ?: throw IllegalStateException("log가 존재하지 않습니다!")
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
                val user : User? = TokenUtil.getAttrFromRequest("userVo") as User?
                val dosa : Dosa? = TokenUtil.getAttrFromRequest("dosaVo") as Dosa?

                val grantedList = ArrayList<SimpleGrantedAuthority>()
                grantedList += SimpleGrantedAuthority("user");
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