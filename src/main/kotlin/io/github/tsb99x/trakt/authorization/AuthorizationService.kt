package io.github.tsb99x.trakt.authorization

import io.github.tsb99x.trakt.INCORRECT_API_TOKEN
import io.github.tsb99x.trakt.USER_IS_NOT_ENABLED
import io.github.tsb99x.trakt.USER_NOT_FOUND
import io.github.tsb99x.trakt.data.ApiTokenDao
import io.github.tsb99x.trakt.data.UserDao
import io.github.tsb99x.trakt.data.UserEntity
import io.github.tsb99x.trakt.exception.AuthException
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthorizationService(
    private val apiTokenDao: ApiTokenDao,
    private val userDao: UserDao
) {

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

        return userEntity

    }

}
