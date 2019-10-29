package io.github.tsb99x.trakt.user

import io.github.tsb99x.trakt.toUUID
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserDao(

    private val jdbcTemplate: JdbcTemplate

) {

    fun findAllWithRoles(): Map<UUID, UserEntity> {

        val users = mutableMapOf<UUID, UserEntity>()

        jdbcTemplate.query( // language=SQL
            """
            
                SELECT
                   u.id as user_id, u.name, u.hash, u.enabled,
                   r.id as role_id, r.name
                FROM users u
                JOIN user_roles ur
                ON ur.user_id = u.id
                JOIN roles r
                ON r.id = ur.role_id;
            
            """.trimIndent()
        ) { rs ->

            val userId = rs.getString("user_id").toUUID()

            val user = users.computeIfAbsent(userId) {
                UserEntity(
                    id = userId,
                    name = rs.getString("name"),
                    hash = rs.getString("hash"),
                    enabled = rs.getBoolean("enabled")
                )
            }

            val role = RoleEntity(
                id = rs.getString("role_id").toUUID(),
                name = rs.getString("name")
            )

            user.roles.add(role)

        }

        return users

    }

}
