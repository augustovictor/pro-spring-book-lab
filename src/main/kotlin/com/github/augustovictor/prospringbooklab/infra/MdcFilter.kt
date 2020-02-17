package com.github.augustovictor.prospringbooklab.infra

import org.slf4j.MDC
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter
class MdcFilter : HttpFilter() {
    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val correlationIdKeyName = "CorrelationId"

        try {
            MDC.put(correlationIdKeyName, getCorrelationId())
            chain.doFilter(request, response)
        } finally {
            MDC.remove(correlationIdKeyName)
        }
    }

    private fun getCorrelationId(): String = UUID.randomUUID().toString()
}