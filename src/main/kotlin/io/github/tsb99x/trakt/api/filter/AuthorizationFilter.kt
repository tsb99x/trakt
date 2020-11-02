package io.github.tsb99x.trakt.api.filter

import io.github.tsb99x.trakt.api.AUTHORIZATION
import io.github.tsb99x.trakt.core.*
import io.github.tsb99x.trakt.core.exception.AuthException
import io.github.tsb99x.trakt.core.service.AuthorizationService
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(
    private val authorizationService: AuthorizationService,
    private val excludePath: String
) : HttpFilter {

    override fun doFilter(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {

        val relUrl = request.requestURI.substring(request.contextPath.length)
        if (relUrl == excludePath) {
            chain.doFilter(request, response)
            return
        }

        val authHeader = request.getHeader(AUTHORIZATION)
            ?: throw AuthException(AUTHORIZATION_HEADER_MUST_BE_PRESENT)

        if (!authHeader.startsWith(BEARER)) {
            throw AuthException(ONLY_AUTHORIZATION_BEARER_IS_SUPPORTED)
        }

        val apiTokenString = authHeader.drop(BEARER.length).trim()

        val apiTokenId = tryOrNull { apiTokenString.toUUID() }
            ?: throw AuthException(API_TOKEN_MUST_BE_UUID)

        val user = authorizationService.authorize(apiTokenId)

        request.setAttribute(API_TOKEN_ID_ATTRIBUTE, apiTokenId)
        request.setAttribute(USER_ATTRIBUTE, user)

        chain.doFilter(request, response)

    }

}
