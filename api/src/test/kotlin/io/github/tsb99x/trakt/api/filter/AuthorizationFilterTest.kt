package io.github.tsb99x.trakt.api.filter

import io.github.tsb99x.trakt.api.AUTHORIZATION
import io.github.tsb99x.trakt.api.interceptor.AuthorizationFilter
import io.github.tsb99x.trakt.core.*
import io.github.tsb99x.trakt.core.exception.AuthException
import io.github.tsb99x.trakt.core.service.AuthorizationService
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest

class AuthorizationFilterTest {

    private val request: HttpServletRequest = mockk {
        every { contextPath } returns "/context"
        every { requestURI } returns "/context/some-request-uri"
    }

    private val filterChain: FilterChain = mockk {
        every { doFilter(any(), any()) } just runs
    }

    private val authorizationService: AuthorizationService = mockk()
    private val interceptor = AuthorizationFilter(authorizationService, "/exclude-me")

    @Test
    fun `expect interceptor to work`() {

        val token = UUID.randomUUID()
        every { request.getHeader(AUTHORIZATION) } returns "$BEARER $token"
        every { request.setAttribute(any(), any()) } just runs
        every { authorizationService.authorize(token) } returns ADMIN_USER

        interceptor.doFilter(request, mockk(), filterChain)

        verify { request.setAttribute(API_TOKEN_ID_ATTRIBUTE, token) }
        verify { request.setAttribute(USER_ATTRIBUTE, ADMIN_USER) }

    }

    @Test
    fun `expect interceptor to ignore excludePath`() {

        every { request.contextPath } returns "/context"
        every { request.requestURI } returns "/context/exclude-me"

        interceptor.doFilter(request, mockk(), filterChain)

        verify { request.contextPath }
        verify { request.requestURI }
        confirmVerified(request)

    }

    @Test
    fun `expect interceptor to throw on no authorization header`() {

        every { request.getHeader(AUTHORIZATION) } returns null

        val ex = assertThrows<AuthException> {
            interceptor.doFilter(request, mockk(), filterChain)
        }

        assertEquals(AUTHORIZATION_HEADER_MUST_BE_PRESENT, ex.localizedMessage)

    }

    @Test
    fun `expect interceptor to throw on non-bearer authorization`() {

        every { request.getHeader(AUTHORIZATION) } returns "Basic YWRtaW46YWRtaW4="

        val ex = assertThrows<AuthException> {
            interceptor.doFilter(request, mockk(), filterChain)
        }

        assertEquals(ONLY_AUTHORIZATION_BEARER_IS_SUPPORTED, ex.localizedMessage)

    }

    @Test
    fun `expect interceptor to throw on not-a-uuid token`() {

        every { request.getHeader(AUTHORIZATION) } returns "$BEARER not-a-uuid"

        val ex = assertThrows<AuthException> {
            interceptor.doFilter(request, mockk(), filterChain)
        }

        assertEquals(API_TOKEN_MUST_BE_UUID, ex.localizedMessage)

    }

}
