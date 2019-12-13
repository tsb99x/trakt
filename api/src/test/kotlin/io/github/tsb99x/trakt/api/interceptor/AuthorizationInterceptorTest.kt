package io.github.tsb99x.trakt.api.interceptor

import io.github.tsb99x.trakt.core.*
import io.github.tsb99x.trakt.core.exception.AuthException
import io.github.tsb99x.trakt.core.service.AuthorizationService
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpHeaders
import java.util.*
import javax.servlet.http.HttpServletRequest

class AuthorizationInterceptorTest {

    private val request: HttpServletRequest = mockk()
    private val authorizationService: AuthorizationService = mockk()
    private val interceptor = AuthorizationInterceptor(authorizationService)

    @Test
    fun `expect interceptor to work`() {

        val token = UUID.randomUUID()
        every { request.getHeader(HttpHeaders.AUTHORIZATION) } returns "$BEARER $token"
        every { request.setAttribute(any(), any()) } just runs
        every { authorizationService.authorize(token) } returns ADMIN_USER

        assertTrue(interceptor.preHandle(request, mockk(), mockk()))

        verify { request.setAttribute(API_TOKEN_ID_ATTRIBUTE, token) }
        verify { request.setAttribute(USER_ATTRIBUTE, ADMIN_USER) }

    }

    @Test
    fun `expect interceptor to throw on no authorization header`() {

        every { request.getHeader(HttpHeaders.AUTHORIZATION) } returns null

        val ex = assertThrows<AuthException> {
            interceptor.preHandle(request, mockk(), mockk())
        }

        assertEquals(AUTHORIZATION_HEADER_MUST_BE_PRESENT, ex.localizedMessage)

    }

    @Test
    fun `expect interceptor to throw on non-bearer authorization`() {

        every { request.getHeader(HttpHeaders.AUTHORIZATION) } returns "Basic YWRtaW46YWRtaW4="

        val ex = assertThrows<AuthException> {
            interceptor.preHandle(request, mockk(), mockk())
        }

        assertEquals(ONLY_AUTHORIZATION_BEARER_IS_SUPPORTED, ex.localizedMessage)

    }

    @Test
    fun `expect interceptor to throw on not-a-uuid token`() {

        every { request.getHeader(HttpHeaders.AUTHORIZATION) } returns "$BEARER not-a-uuid"

        val ex = assertThrows<AuthException> {
            interceptor.preHandle(request, mockk(), mockk())
        }

        assertEquals(API_TOKEN_MUST_BE_UUID, ex.localizedMessage)

    }

}
