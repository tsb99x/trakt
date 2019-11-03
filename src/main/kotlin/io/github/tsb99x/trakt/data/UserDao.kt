package io.github.tsb99x.trakt.data

import io.github.tsb99x.trakt.getUUID
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class UserDao(
    private val jdbcTemplate: JdbcTemplate
) {

    private val userHeaders = "id, name, hash, enabled"
    private val userMapper = RowMapper { rs, _ ->

        UserEntity(
            id = rs.getUUID("id"),
            name = rs.getString("name"),
            hash = rs.getString("hash"),
            enabled = rs.getBoolean("enabled")
        )

    }

    fun findUserByUsername(
        username: String
    ): UserEntity? {

        try {

            return jdbcTemplate.queryForObject( // language=SQL
                """

                    SELECT $userHeaders
                    FROM users
                    WHERE name = ?;
                
                """.trimIndent(),
                arrayOf(username),
                userMapper
            )

        } catch (ex: EmptyResultDataAccessException) {

            return null

        }

    }

}
