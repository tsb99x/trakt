package io.github.tsb99x.trakt.user

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class UserDao(

    val jdbcTemplate: JdbcTemplate

) {

    fun findAllWithRoles(): Map<Long, UserEntity> {

        val users = mutableMapOf<Long, UserEntity>()

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

            val userId = rs.getLong("user_id")

            val user = users.computeIfAbsent(userId) {
                UserEntity(
                    id = userId,
                    name = rs.getString("name"),
                    hash = rs.getString("hash"),
                    enabled = rs.getBoolean("enabled")
                )
            }

            val role = RoleEntity(
                id = rs.getLong("role_id"),
                name = rs.getString("name")
            )

            user.roles.add(role)

        }

        return users

    }

}
