package io.github.tsb99x.trakt.api.servlet

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.tsb99x.trakt.api.sendJson
import io.github.tsb99x.trakt.core.USER_ATTRIBUTE
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_OK

class DummyServlet(
    private val objectMapper: ObjectMapper
) : HttpServlet() {

    override fun doGet(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {

        val userEntity = request.getAttribute(USER_ATTRIBUTE)

        response.sendJson(SC_OK, objectMapper, userEntity)

    }

}
