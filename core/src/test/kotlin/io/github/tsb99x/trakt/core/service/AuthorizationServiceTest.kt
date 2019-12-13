package io.github.tsb99x.trakt.core.service

import io.github.tsb99x.trakt.core.*
import io.github.tsb99x.trakt.core.entity.ApiTokenEntity
import io.github.tsb99x.trakt.core.exception.AuthException
import io.github.tsb99x.trakt.data.dao.ApiTokenDao
import io.github.tsb99x.trakt.data.dao.UserDao
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant
import java.util.*

class AuthorizationServiceTest {

    private val apiTokenDao: ApiTokenDao = mockk()
    private val userDao: UserDao = mockk {
        every { selectOneByUsername("admin") } returns ADMIN_USER
    }
    private val authorizationService = AuthorizationService(apiTokenDao, userDao)

    @Test
    fun `expect authorize to work`() {

        val now = Instant.now()
        val apiTokenEntity = ApiTokenEntity(UUID.randomUUID(), ADMIN_USER.id, now, now)
        every { apiTokenDao.selectOneById(apiTokenEntity.id) } returns apiTokenEntity
        every { apiTokenDao.updateLastUsedAtById(any()) } just runs
        every { userDao.selectOneById(ADMIN_USER.id) } returns ADMIN_USER

        val res = authorizationService.authorize(apiTokenEntity.id)

        assertEquals(ADMIN_USER, res)
        verify { apiTokenDao.updateLastUsedAtById(apiTokenEntity.id) }

    }

    @Test
    fun `expect authorize to throw on incorrect api token`() {

        every { apiTokenDao.selectOneById(any()) } returns null

        val ex = assertThrows<AuthException> {
            authorizationService.authorize(UUID.randomUUID())
        }

        assertEquals(INCORRECT_API_TOKEN, ex.localizedMessage)

    }

    @Test
    fun `expect authorize to throw on non-existing user`() {

        val now = Instant.now()
        val apiTokenEntity = ApiTokenEntity(UUID.randomUUID(), UUID.randomUUID(), now, now)
        every { apiTokenDao.selectOneById(apiTokenEntity.id) } returns apiTokenEntity
        every { userDao.selectOneById(any()) } returns null

        val ex = assertThrows<AuthException> {
            authorizationService.authorize(apiTokenEntity.id)
        }

        assertEquals(USER_NOT_FOUND, ex.localizedMessage)

    }

    @Test
    fun `expect authorize to throw on non-enabled user`() {

        val tempUserEntity = ADMIN_USER.copy(enabled = false)
        val now = Instant.now()
        val apiTokenEntity = ApiTokenEntity(UUID.randomUUID(), tempUserEntity.id, now, now)
        every { apiTokenDao.selectOneById(apiTokenEntity.id) } returns apiTokenEntity
        every { userDao.selectOneById(ADMIN_USER.id) } returns tempUserEntity

        val ex = assertThrows<AuthException> {
            authorizationService.authorize(apiTokenEntity.id)
        }

        assertEquals(USER_IS_NOT_ENABLED, ex.localizedMessage)

    }

    @Test
    fun `expect login to work`() {

        every { apiTokenDao.insert(any()) } just runs

        val token = authorizationService.login(ADMIN_USER.name, "admin")

        verify { apiTokenDao.insert(token) }
        confirmVerified(apiTokenDao)

    }

    @Test
    fun `expect login to throw on wrong username`() {

        every { userDao.selectOneByUsername(any()) } returns null

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

        every { apiTokenDao.insert(any()) } just runs

        val token = authorizationService.login(ADMIN_USER.name, "admin")

        clearMocks(apiTokenDao)
        every { apiTokenDao.deleteById(any()) } just runs

        authorizationService.logout(token.id)

        verify { apiTokenDao.deleteById(token.id) }
        confirmVerified(apiTokenDao)

    }

}
