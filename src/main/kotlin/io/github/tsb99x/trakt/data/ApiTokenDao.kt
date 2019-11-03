package io.github.tsb99x.trakt.data

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class ApiTokenDao(
    private val jdbcTemplate: JdbcTemplate
) {

    private val rowHeaders = "id, user_id, created_at"
    private val rowArity = rowHeaders.split(",").joinToString(",") { "?" }

    fun insert(
        apiToken: ApiTokenEntity
    ) {

        jdbcTemplate.update( // language=SQL
            """
                
                INSERT INTO api_tokens ($rowHeaders)
                VALUES ($rowArity)
                
            """.trimIndent(),
            apiToken.id, apiToken.userId, apiToken.createdAt
        )

    }

}
