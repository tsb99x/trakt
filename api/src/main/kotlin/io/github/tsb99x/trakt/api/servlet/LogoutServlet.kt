package io.github.tsb99x.trakt.api.servlet

import io.github.tsb99x.trakt.api.reqAttr
import io.github.tsb99x.trakt.core.API_TOKEN_ID_ATTRIBUTE
import io.github.tsb99x.trakt.core.service.AuthorizationService
import java.util.*
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LogoutServlet(
    private val authorizationService: AuthorizationService
) : HttpServlet() {

    override fun doPost(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {

        val apiTokenId: UUID = request.reqAttr(API_TOKEN_ID_ATTRIBUTE)

        authorizationService.logout(apiTokenId)

        response.status = HttpServletResponse.SC_OK

    }

}
