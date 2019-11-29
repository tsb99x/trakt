package io.github.tsb99x.trakt.core.service

import com.nhaarman.mockitokotlin2.*
import io.github.tsb99x.trakt.core.*
import io.github.tsb99x.trakt.core.data.ApiTokenDao
import io.github.tsb99x.trakt.core.data.ApiTokenEntity
import io.github.tsb99x.trakt.core.data.UserDao
import io.github.tsb99x.trakt.core.exception.AuthException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.Instant
import java.util.*

class AuthorizationServiceTest {

    private val apiTokenDao: ApiTokenDao = mock()
    private val userDao: UserDao = mock()
    private val passwordEncoder = BCryptPasswordEncoder()
    private val authorizationService =
        AuthorizationService(apiTokenDao, userDao, passwordEncoder)

    @BeforeEach
    fun beforeEach() {

        doReturn(ADMIN_USER).whenever(userDao).selectOneByUsername("admin")

    }

    @Test
    fun `expect authorize to work`() {

        val now = Instant.now()
        val apiTokenEntity = ApiTokenEntity(UUID.randomUUID(), ADMIN_USER.id, now, now)
        doReturn(apiTokenEntity).whenever(apiTokenDao).selectOneById(apiTokenEntity.id)
        doReturn(ADMIN_USER).whenever(userDao).selectOneById(ADMIN_USER.id)

        val res = authorizationService.authorize(apiTokenEntity.id)

        assertEquals(ADMIN_USER, res)
        verify(apiTokenDao).updateLastUsedAtById(apiTokenEntity.id)

    }

    @Test
    fun `expect authorize to throw on incorrect api token`() {

        val ex = assertThrows<AuthException> {
            authorizationService.authorize(UUID.randomUUID())
        }

        assertEquals(INCORRECT_API_TOKEN, ex.localizedMessage)

    }

    @Test
    fun `expect authorize to throw on non-existing user`() {

        val now = Instant.now()
        val apiTokenEntity =
            ApiTokenEntity(UUID.randomUUID(), UUID.randomUUID(), now, now)
        doReturn(apiTokenEntity).whenever(apiTokenDao).selectOneById(apiTokenEntity.id)

        val ex = assertThrows<AuthException> {
            authorizationService.authorize(apiTokenEntity.id)
        }

        assertEquals(USER_NOT_FOUND, ex.localizedMessage)

    }

    @Test
    fun `expect authorize to throw on non-enabled user`() {

        val tempUserEntity = ADMIN_USER.copy(enabled = false)
        val now = Instant.now()
        val apiTokenEntity =
            ApiTokenEntity(UUID.randomUUID(), tempUserEntity.id, now, now)
        doReturn(apiTokenEntity).whenever(apiTokenDao).selectOneById(apiTokenEntity.id)
        doReturn(tempUserEntity).whenever(userDao).selectOneById(ADMIN_USER.id)

        val ex = assertThrows<AuthException> {
            authorizationService.authorize(apiTokenEntity.id)
        }

        assertEquals(USER_IS_NOT_ENABLED, ex.localizedMessage)

    }

    @Test
    fun `expect login to work`() {

        val token = authorizationService.login(ADMIN_USER.name, "admin")

        verify(apiTokenDao).insert(token)
        verifyNoMoreInteractions(apiTokenDao)

    }

    @Test
    fun `expect login to throw on wrong username`() {

        val ex = assertThrows<AuthException> {
            authorizationService.login("incorrect-username", "admin")
        }

        assertEquals(USERNAME_AND_PASSWORD_COMBO_NOT_FOUND, ex.localizedMessage)

    }

    @Test
    fun `expect login to throw on wrong password`() {

        val ex = assertThrows<AuthException> {
            authorizationService.login("admin", "incorrect-password")
        }

        assertEquals(USERNAME_AND_PASSWORD_COMBO_NOT_FOUND, ex.localizedMessage)

    }

    @Test
    fun `expect logout to work`() {

        val token = authorizationService.login(ADMIN_USER.name, "admin")
        reset(apiTokenDao)

        authorizationService.logout(token.id)

        verify(apiTokenDao).deleteById(token.id)
        verifyNoMoreInteractions(apiTokenDao)

    }

}
