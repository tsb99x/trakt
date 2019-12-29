package io.github.tsb99x.trakt.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

const val AUTHORIZATION = "Authorization"
const val APPLICATION_JSON = "application/json"
const val CONTENT_TYPE = "Content-Type"

interface HttpFilter : Filter {

    fun doFilter(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    )

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {

        if (request is HttpServletRequest && response is HttpServletResponse) {
            doFilter(request, response, chain)
        } else {
            throw ServletException("Failed to filter non-HTTP request")
        }

    }

    override fun destroy() {}

    override fun init(
        filterConfig: FilterConfig
    ) {}

}

inline fun <reified T> HttpServletRequest.reqAttr(name: String): T =
    getAttribute(name) as T

inline fun <reified T> HttpServletRequest.reqBody(objectMapper: ObjectMapper): T =
    objectMapper.readValue(inputStream)

fun HttpServletResponse.sendJson(statusCode: Int, objectMapper: ObjectMapper, value: Any) {
    status = statusCode
    contentType = APPLICATION_JSON
    objectMapper.writeValue(outputStream, value)
}
