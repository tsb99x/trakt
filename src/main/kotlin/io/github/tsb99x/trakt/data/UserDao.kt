package io.github.tsb99x.trakt.data

import io.github.tsb99x.trakt.getUUID
import io.github.tsb99x.trakt.queryForObjectOrNull
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserDao(
    private val jdbcTemplate: JdbcTemplate
) {

    private val tableName = "users"
    private val rowHeaders = "id, name, hash, enabled"

    private val rowMapper = RowMapper { rs, _ ->

        UserEntity(
            id = rs.getUUID("id"),
            name = rs.getString("name"),
            hash = rs.getString("hash"),
            enabled = rs.getBoolean("enabled")
        )

    }

    fun selectOneByUsername(
        username: String
    ): UserEntity? {

        return jdbcTemplate.queryForObjectOrNull(
            """

                SELECT $rowHeaders
                FROM $tableName
                WHERE name = ?
            
            """.trimIndent(),
            arrayOf(username),
            rowMapper
        )

    }

    fun selectOneById(
        id: UUID
    ): UserEntity? {

        return jdbcTemplate.queryForObjectOrNull(
            """
                
                SELECT $rowHeaders
                FROM $tableName
                WHERE id = ?
                
            """.trimIndent(),
            arrayOf(id),
            rowMapper
        )

    }

}
