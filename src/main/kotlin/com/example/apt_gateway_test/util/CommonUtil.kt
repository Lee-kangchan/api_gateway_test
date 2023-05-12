package com.example.apt_gateway_test.util

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

class CommonUtil {
    companion object{

        fun getAttrFromRequest(key: String?): Any? {
            val attr = RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes
            return attr.getAttribute(key!!, ServletRequestAttributes.SCOPE_REQUEST)
        }
    }
}