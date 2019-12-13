package io.github.tsb99x.trakt.core.service

import io.github.tsb99x.trakt.core.INCORRECT_API_TOKEN
import io.github.tsb99x.trakt.core.USERNAME_AND_PASSWORD_COMBO_NOT_FOUND
import io.github.tsb99x.trakt.core.USER_IS_NOT_ENABLED
import io.github.tsb99x.trakt.core.USER_NOT_FOUND
import io.github.tsb99x.trakt.core.entity.ApiTokenEntity
import io.github.tsb99x.trakt.core.entity.UserEntity
import io.github.tsb99x.trakt.core.exception.AuthException
import io.github.tsb99x.trakt.data.dao.ApiTokenDao
import io.github.tsb99x.trakt.data.dao.UserDao
import org.mindrot.jbcrypt.BCrypt
import java.time.Instant
import java.util.*

open class AuthorizationService(
    private val apiTokenDao: ApiTokenDao,
    private val userDao: UserDao
) {

    fun login(
        username: String,
        password: String
    ): ApiTokenEntity {

        val user = userDao.selectOneByUsername(username)
        if (user == null || !BCrypt.checkpw(password, user.hash)) {
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
