package io.github.tsb99x.trakt.data

import io.github.tsb99x.trakt.*
import io.github.tsb99x.trakt.config.ConfigProperties
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Repository
class ApiTokenDao(
    private val jdbcTemplate: JdbcTemplate,
    private val configProperties: ConfigProperties
) {

    private val tableName = "api_tokens"
    private val rowHeaders = "id, user_id, created_at, last_used_at"
    private val rowArity = rowHeaders.split(",").joinToString(",") { "?" }

    private val rowMapper: RowMapper<ApiTokenEntity> = RowMapper { rs, _ ->

        ApiTokenEntity(
            id = rs.getUUID("id"),
            userId = rs.getUUID("user_id"),
            createdAt = rs.getInstant("created_at"),
            lastUsedAt = rs.getInstant("last_used_at")
        )

    }

    fun insert(
        apiToken: ApiTokenEntity
    ) {

        jdbcTemplate.update(
            """
                
                INSERT INTO $tableName ($rowHeaders)
                VALUES ($rowArity)
                
            """.trimIndent(),
            apiToken.id, apiToken.userId, apiToken.createdAt.toUTC(), apiToken.lastUsedAt.toUTC()
        )

    }

    fun selectOneById(
        id: UUID
    ): ApiTokenEntity? {

        val expirationHorizon = Instant.now().minusMinutes(configProperties.tokenLifetimeInMinutes)

        return jdbcTemplate.queryForObjectOrNull(
            """
            
                SELECT $rowHeaders
                FROM $tableName
                WHERE id = ?
                AND last_used_at >= ?
                
            """.trimIndent(),
            arrayOf(id, expirationHorizon.toUTC()),
            rowMapper
        )

    }

    fun deleteById(
        id: UUID
    ) {

        jdbcTemplate.update(
            """
                
                DELETE FROM $tableName
                WHERE id = ?
                
            """.trimIndent(),
            id
        )

    }

    fun updateLastUsedAtById(
        id: UUID
    ) {

        jdbcTemplate.update(
            """
                
                UPDATE $tableName
                SET last_used_at = ?
                WHERE id = ?
                
            """.trimIndent(),
            Instant.now().toUTC(), id
        )

    }

}
