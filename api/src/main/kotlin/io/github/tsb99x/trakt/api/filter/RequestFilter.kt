package io.github.tsb99x.trakt.api.filter

import io.github.tsb99x.trakt.api.FINISHED
import io.github.tsb99x.trakt.api.HttpFilter
import io.github.tsb99x.trakt.api.SERVING
import io.github.tsb99x.trakt.core.REQUEST_ID
import io.github.tsb99x.trakt.core.classLogger
import org.slf4j.MDC
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RequestFilter : HttpFilter {

    private val logger = classLogger()

    override fun doFilter(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {

        MDC.clear()

        val requestId = UUID.randomUUID()
        MDC.put(REQUEST_ID, requestId.toString())
        request.setAttribute(REQUEST_ID, requestId)

        logger.info(SERVING)

        chain.doFilter(request, response)

        logger.info(FINISHED)

    }

}
