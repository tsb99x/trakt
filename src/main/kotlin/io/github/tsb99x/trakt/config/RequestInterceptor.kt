package io.github.tsb99x.trakt.config

import io.github.tsb99x.trakt.REQUEST_ID
import io.github.tsb99x.trakt.classLogger
import org.jboss.logging.MDC
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class RequestInterceptor : HandlerInterceptor {

    private val logger = classLogger()

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {

        MDC.clear()

        val requestId = UUID.randomUUID()
        MDC.put(REQUEST_ID, requestId)
        request.setAttribute(REQUEST_ID, requestId)

        logger.info("Serving")

        return true

    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {

        logger.info("Finished")

    }

}