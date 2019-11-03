package io.github.tsb99x.trakt.authentication

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.github.tsb99x.trakt.adminUser
import io.github.tsb99x.trakt.authentication.AuthenticationService.Companion.USERNAME_AND_PASSWORD_COMBO_MESSAGE
import io.github.tsb99x.trakt.data.ApiTokenDao
import io.github.tsb99x.trakt.data.ApiTokenEntity
import io.github.tsb99x.trakt.data.UserDao
import io.github.tsb99x.trakt.exception.GenericException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class AuthenticationServiceTest {

    private val userDao: UserDao = mock()
    private val apiTokenDao: ApiTokenDao = mock()
    private val passwordEncoder = BCryptPasswordEncoder()
    private val authenticationService = AuthenticationService(userDao, apiTokenDao, passwordEncoder)

    @BeforeEach
    fun beforeEach() {

        doReturn(adminUser).whenever(userDao).findUserByUsername("admin")

    }

    @Test
    fun `expect authenticate to work`() {

        val token = authenticationService.authenticate("admin", "admin")

        val captor = argumentCaptor<ApiTokenEntity>()
        verify(apiTokenDao).insert(captor.capture())
        verifyNoMoreInteractions(apiTokenDao)

        assertEquals(captor.firstValue, token)

    }

    @Test
    fun `expect authenticate to throw on wrong username`() {

        val ex = assertThrows<GenericException> {
            authenticationService.authenticate("incorrect-username", "admin")
        }

        assertEquals(USERNAME_AND_PASSWORD_COMBO_MESSAGE, ex.localizedMessage)

    }

    @Test
    fun `expect authenticate to throw on wrong password`() {

        val ex = assertThrows<GenericException> {
            authenticationService.authenticate("admin", "incorrect-password")
        }

        assertEquals(USERNAME_AND_PASSWORD_COMBO_MESSAGE, ex.localizedMessage)

    }

}
