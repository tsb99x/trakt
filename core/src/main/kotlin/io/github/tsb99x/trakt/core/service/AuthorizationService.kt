package io.github.tsb99x.trakt.core.service

import io.github.tsb99x.trakt.core.INCORRECT_API_TOKEN
import io.github.tsb99x.trakt.core.USERNAME_AND_PASSWORD_COMBO_NOT_FOUND
import io.github.tsb99x.trakt.core.USER_IS_NOT_ENABLED
import io.github.tsb99x.trakt.core.USER_NOT_FOUND
import io.github.tsb99x.trakt.core.data.ApiTokenDao
import io.github.tsb99x.trakt.core.data.ApiTokenEntity
import io.github.tsb99x.trakt.core.data.UserDao
import io.github.tsb99x.trakt.core.data.UserEntity
import io.github.tsb99x.trakt.core.exception.AuthException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class AuthorizationService(
    private val apiTokenDao: ApiTokenDao,
    private val userDao: UserDao,
    private val passwordEncoder: PasswordEncoder
) {

    fun login(
        username: String,
        password: String
    ): ApiTokenEntity {

        val user = userDao.selectOneByUsername(username)
        if (user == null || !passwordEncoder.matches(password, user.hash)) {
            throw AuthException(USERNAME_AND_PASSWORD_COMBO_NOT_FOUND)
        }

        val now = Instant.now()
        val apiToken = ApiTokenEntity(UUID.randomUUID(), user.id, now, now)
        apiTokenDao.insert(apiToken)

        return apiToken

    }

    fun authorize(
        apiTokenId: UUID
    ): UserEntity {

        val apiTokenEntity = apiTokenDao.selectOneById(apiTokenId)
            ?: throw AuthException(INCORRECT_API_TOKEN)

        val userEntity = userDao.selectOneById(apiTokenEntity.userId)
            ?: throw AuthException(USER_NOT_FOUND)

        if (!userEntity.enabled) {
            throw AuthException(USER_IS_NOT_ENABLED)
        }

        apiTokenDao.updateLastUsedAtById(apiTokenEntity.id)

        return userEntity

    }

    fun logout(
        apiTokenId: UUID
    ) {

        apiTokenDao.deleteById(apiTokenId)

    }

}
