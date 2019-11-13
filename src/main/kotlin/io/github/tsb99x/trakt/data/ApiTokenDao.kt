package io.github.tsb99x.trakt.data

import io.github.tsb99x.trakt.getInstant
import io.github.tsb99x.trakt.getUUID
import io.github.tsb99x.trakt.queryForObjectOrNull
import io.github.tsb99x.trakt.toUTC
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ApiTokenDao(
    private val jdbcTemplate: JdbcTemplate
) {

    private val rowHeaders = "id, user_id, created_at"
    private val rowArity = rowHeaders.split(",").joinToString(",") { "?" }

    private val rowMapper: RowMapper<ApiTokenEntity> = RowMapper { rs, _ ->

        ApiTokenEntity(
            id = rs.getUUID("id"),
            userId = rs.getUUID("user_id"),
            createdAt = rs.getInstant("created_at")
        )

    }

    fun insert(
        apiToken: ApiTokenEntity
    ) {

        jdbcTemplate.update(
            """
                
                INSERT INTO api_tokens ($rowHeaders)
                VALUES ($rowArity)
                
            """.trimIndent(),
            apiToken.id, apiToken.userId, apiToken.createdAt.toUTC()
        )

    }

    fun selectOneById(
        id: UUID
    ): ApiTokenEntity? {

        return jdbcTemplate.queryForObjectOrNull(
            """
            
                SELECT $rowHeaders
                FROM api_tokens
                WHERE id = ?
                
            """.trimIndent(),
            arrayOf(id),
            rowMapper
        )

    }

}
