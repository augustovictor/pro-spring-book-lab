package com.github.augustovictor.prospringbooklab.infra

import org.slf4j.MDC
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class MdcInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val correlationId = request.getHeader("CorrelationId") ?: getCorrelationId()

        MDC.put("CorrelationId", correlationId)
        return true
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        MDC.remove("CorrelationId")
    }

    private fun getCorrelationId(): String = UUID.randomUUID().toString()
}