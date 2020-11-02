package io.github.tsb99x.trakt.api.filter

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.tsb99x.trakt.api.model.ApiErrorResponse
import io.github.tsb99x.trakt.core.REQUEST_ID
import io.github.tsb99x.trakt.core.classLogger
import io.github.tsb99x.trakt.core.exception.AuthException
import io.github.tsb99x.trakt.core.exception.GenericException
import io.github.tsb99x.trakt.core.toUUID
import org.slf4j.MDC
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.*

class ExceptionFilter(
    private val objectMapper: ObjectMapper
): HttpFilter {

    private val logger = classLogger()

    override fun doFilter(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {

        try {

            chain.doFilter(request, response)

        } catch (ex: GenericException) {

            val desc = "Generic exception occurred"
            logger.info(desc, ex)
            sendResponse(response, ex.localizedMessage, SC_BAD_REQUEST)

        } catch (ex: AuthException) {

            val desc = "Auth exception occurred"
            logger.warn(desc, ex)
            sendResponse(response, ex.localizedMessage, SC_UNAUTHORIZED)

        } catch (ex: Throwable) {

            val desc = "Internal server error"
            logger.error(desc, ex)
            sendResponse(response, desc, SC_INTERNAL_SERVER_ERROR)

        }

    }

    private fun sendResponse(
        response: HttpServletResponse,
        desc: String,
        statusCode: Int
    ) {

        val requestId = MDC.get(REQUEST_ID).toUUID()
        val body = ApiErrorResponse(requestId, desc)
        val json = objectMapper.writeValueAsString(body)

        response.status = statusCode
        response.contentType = "application/json"
        response.writer.println(json)
        response.writer.close() // No one should be using OutputStream after ExceptionFilter.

    }

}
