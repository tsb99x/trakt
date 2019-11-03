package io.github.tsb99x.trakt.authentication

import io.github.tsb99x.trakt.data.ApiTokenDao
import io.github.tsb99x.trakt.data.ApiTokenEntity
import io.github.tsb99x.trakt.data.UserDao
import io.github.tsb99x.trakt.exception.GenericException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class AuthenticationService(
    private val userDao: UserDao,
    private val apiTokenDao: ApiTokenDao,
    private val passwordEncoder: PasswordEncoder
) {

    companion object {
        const val USERNAME_AND_PASSWORD_COMBO_MESSAGE = "Such combination of username and password was not found"
    }

    fun authenticate(
        username: String,
        password: String
    ): ApiTokenEntity {

        val user = userDao.findUserByUsername(username)
        if (user == null || !passwordEncoder.matches(password, user.hash)) {
            throw GenericException(USERNAME_AND_PASSWORD_COMBO_MESSAGE)
        }

        val apiToken = ApiTokenEntity(UUID.randomUUID(), user.id, LocalDateTime.now())
        apiTokenDao.insert(apiToken)

        return apiToken

    }

}
