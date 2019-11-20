package io.github.tsb99x.trakt.rest.interceptor

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.github.tsb99x.trakt.*
import io.github.tsb99x.trakt.exception.AuthException
import io.github.tsb99x.trakt.service.AuthorizationService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpHeaders
import java.util.*
import javax.servlet.http.HttpServletRequest

class AuthorizationInterceptorTest {

    private val request: HttpServletRequest = mock()
    private val authorizationService: AuthorizationService = mock()
    private val interceptor = AuthorizationInterceptor(authorizationService)

    @Test
    fun `expect interceptor to work`() {

        val token = UUID.randomUUID()
        doReturn("$BEARER $token").whenever(request).getHeader(HttpHeaders.AUTHORIZATION)
        doReturn(ADMIN_USER).whenever(authorizationService).authorize(token)

        assertTrue(interceptor.preHandle(request, mock(), mock()))

        verify(request).setAttribute(API_TOKEN_ID_ATTRIBUTE, token)
        verify(request).setAttribute(USER_ATTRIBUTE, ADMIN_USER)

    }

    @Test
    fun `expect interceptor to throw on no authorization header`() {

        val ex = assertThrows<AuthException> {
            interceptor.preHandle(request, mock(), mock())
        }

        assertEquals(AUTHORIZATION_HEADER_MUST_BE_PRESENT, ex.localizedMessage)

    }

    @Test
    fun `expect interceptor to throw on non-bearer authorization`() {

        doReturn("Basic YWRtaW46YWRtaW4=").whenever(request).getHeader(HttpHeaders.AUTHORIZATION)

        val ex = assertThrows<AuthException> {
            interceptor.preHandle(request, mock(), mock())
        }

        assertEquals(ONLY_AUTHORIZATION_BEARER_IS_SUPPORTED, ex.localizedMessage)

    }

    @Test
    fun `expect interceptor to throw on not-a-uuid token`() {

        doReturn("$BEARER not-a-uuid").whenever(request).getHeader(HttpHeaders.AUTHORIZATION)

        val ex = assertThrows<AuthException> {
            interceptor.preHandle(request, mock(), mock())
        }

        assertEquals(API_TOKEN_MUST_BE_UUID, ex.localizedMessage)

    }

}
