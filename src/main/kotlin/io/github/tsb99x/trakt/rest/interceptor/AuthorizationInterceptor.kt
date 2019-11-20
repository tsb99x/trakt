package io.github.tsb99x.trakt.rest.interceptor

import io.github.tsb99x.trakt.*
import io.github.tsb99x.trakt.exception.AuthException
import io.github.tsb99x.trakt.service.AuthorizationService
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthorizationInterceptor(
    private val authorizationService: AuthorizationService
) : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {

        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
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

        return true

    }

}
