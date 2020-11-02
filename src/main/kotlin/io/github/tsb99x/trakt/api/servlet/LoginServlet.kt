package io.github.tsb99x.trakt.api.servlet

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.tsb99x.trakt.api.model.LoginRequest
import io.github.tsb99x.trakt.api.model.LoginResponse
import io.github.tsb99x.trakt.api.reqAttr
import io.github.tsb99x.trakt.api.reqBody
import io.github.tsb99x.trakt.api.sendJson
import io.github.tsb99x.trakt.core.REQUEST_ID
import io.github.tsb99x.trakt.core.service.AuthorizationService
import java.util.*
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_OK

class LoginServlet(
    private val authorizationService: AuthorizationService,
    private val objectMapper: ObjectMapper
) : HttpServlet() {

    override fun doPost(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {

        val requestId: UUID = request.reqAttr(REQUEST_ID)
        val body: LoginRequest = request.reqBody(objectMapper)

        val apiToken = authorizationService.login(body.username, body.password)

        val res = LoginResponse(requestId, apiToken.id)
        response.sendJson(SC_OK, objectMapper, res)

    }

}
