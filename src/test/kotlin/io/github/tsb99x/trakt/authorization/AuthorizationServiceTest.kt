package io.github.tsb99x.trakt.authorization

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.github.tsb99x.trakt.INCORRECT_API_TOKEN
import io.github.tsb99x.trakt.USER_IS_NOT_ENABLED
import io.github.tsb99x.trakt.USER_NOT_FOUND
import io.github.tsb99x.trakt.adminUser
import io.github.tsb99x.trakt.data.ApiTokenDao
import io.github.tsb99x.trakt.data.ApiTokenEntity
import io.github.tsb99x.trakt.data.UserDao
import io.github.tsb99x.trakt.exception.AuthException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant
import java.util.*

class AuthorizationServiceTest {

    private val apiTokenDao: ApiTokenDao = mock()
    private val userDao: UserDao = mock()
    private val authorizationService = AuthorizationService(apiTokenDao, userDao)

    @Test
    fun `expect authorize to work`() {

        val apiTokenEntity = ApiTokenEntity(UUID.randomUUID(), adminUser.id, Instant.now())
        doReturn(apiTokenEntity).whenever(apiTokenDao).selectOneById(apiTokenEntity.id)
        doReturn(adminUser).whenever(userDao).selectOneById(adminUser.id)

        val res = authorizationService.authorize(apiTokenEntity.id)

        assertEquals(adminUser, res)

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

        val apiTokenEntity = ApiTokenEntity(UUID.randomUUID(), UUID.randomUUID(), Instant.now())
        doReturn(apiTokenEntity).whenever(apiTokenDao).selectOneById(apiTokenEntity.id)

        val ex = assertThrows<AuthException> {
            authorizationService.authorize(apiTokenEntity.id)
        }

        assertEquals(USER_NOT_FOUND, ex.localizedMessage)

    }

    @Test
    fun `expect authorize to throw on non-enabled user`() {

        val tempUserEntity = adminUser.copy(enabled = false)
        val apiTokenEntity = ApiTokenEntity(UUID.randomUUID(), tempUserEntity.id, Instant.now())
        doReturn(apiTokenEntity).whenever(apiTokenDao).selectOneById(apiTokenEntity.id)
        doReturn(tempUserEntity).whenever(userDao).selectOneById(adminUser.id)

        val ex = assertThrows<AuthException> {
            authorizationService.authorize(apiTokenEntity.id)
        }

        assertEquals(USER_IS_NOT_ENABLED, ex.localizedMessage)

    }

}
