package io.github.tsb99x.trakt.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

const val APPLICATION_JSON = "application/json"
const val AUTH_EXCEPTION_OCCURRED = "Auth exception occurred"
const val AUTHORIZATION = "Authorization"
const val CONTENT_TYPE = "Content-Type"
const val FAILED_TO_FILTER_NON_HTTP_REQUEST = "Failed to filter non-HTTP request"
const val FINISHED = "Finished"
const val GENERIC_EXCEPTION_OCCURRED = "Generic exception occurred"
const val INTERNAL_SERVER_ERROR = "Internal server error"
const val SERVING = "Serving"

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
            throw ServletException(FAILED_TO_FILTER_NON_HTTP_REQUEST)
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
