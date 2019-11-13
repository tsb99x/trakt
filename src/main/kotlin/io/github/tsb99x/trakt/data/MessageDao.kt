package io.github.tsb99x.trakt.data

import io.github.tsb99x.trakt.getInstant
import io.github.tsb99x.trakt.getUUID
import io.github.tsb99x.trakt.toUTC
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class MessageDao(
    private val jdbcTemplate: JdbcTemplate
) {

    private val rowHeaders = "id, text, created_at"
    private val rowArity = rowHeaders.split(",").joinToString(",") { "?" }

    private val rowMapper = RowMapper { rs, _ ->

        MessageEntity(
            id = rs.getUUID("id"),
            text = rs.getString("text"),
            createdAt = rs.getInstant("created_at")
        )

    }

    fun insert(
        entity: MessageEntity
    ) {

        jdbcTemplate.update(
            """
            
                INSERT INTO messages ($rowHeaders)
                VALUES ($rowArity)
            
            """.trimIndent(),
            entity.id, entity.text, entity.createdAt.toUTC()
        )

    }

    fun findAllOrderByCreationTimeDesc(): List<MessageEntity> {

        return jdbcTemplate.query(
            """
            
                SELECT $rowHeaders
                FROM messages
                ORDER BY created_at DESC
            
            """.trimIndent(),
            rowMapper
        )

    }

}
