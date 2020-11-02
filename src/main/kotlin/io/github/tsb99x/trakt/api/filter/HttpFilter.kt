package io.github.tsb99x.trakt.api.filter

import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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
