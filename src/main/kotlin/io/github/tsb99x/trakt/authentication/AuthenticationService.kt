package io.github.tsb99x.trakt.authentication

import io.github.tsb99x.trakt.USERNAME_AND_PASSWORD_COMBO_NOT_FOUND
import io.github.tsb99x.trakt.data.ApiTokenDao
import io.github.tsb99x.trakt.data.ApiTokenEntity
import io.github.tsb99x.trakt.data.UserDao
import io.github.tsb99x.trakt.exception.AuthException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class AuthenticationService(
    private val userDao: UserDao,
    private val apiTokenDao: ApiTokenDao,
    private val passwordEncoder: PasswordEncoder
) {

    fun authenticate(
        username: String,
        password: String
    ): ApiTokenEntity {

        val user = userDao.selectOneByUsername(username)
        if (user == null || !passwordEncoder.matches(password, user.hash)) {
            throw AuthException(USERNAME_AND_PASSWORD_COMBO_NOT_FOUND)
        }

        val apiToken = ApiTokenEntity(UUID.randomUUID(), user.id, Instant.now())
        apiTokenDao.insert(apiToken)

        return apiToken

    }

}
