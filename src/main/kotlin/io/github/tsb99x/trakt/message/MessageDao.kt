package io.github.tsb99x.trakt.message

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MessageDao(

    private val jdbcTemplate: JdbcTemplate

) {

    val rowHeaders = "id, text, created_at"

    val rowMapper = RowMapper { rs, _ ->

        MessageEntity(
            id = UUID.fromString(rs.getString("id")),
            text = rs.getString("text"),
            createdAt = rs.getTimestamp("created_at").toLocalDateTime()
        )

    }

    fun create(entity: MessageEntity) {

        jdbcTemplate.update( // language=SQL
            """
            
                INSERT INTO messages ($rowHeaders)
                VALUES (?, ?, ?);
            
            """.trimIndent(),
            entity.id, entity.text, entity.createdAt
        )

    }

    fun findAllOrderByCreationTimeDesc(): List<MessageEntity> {

        return jdbcTemplate.query( // language=SQL
            """
            
                SELECT $rowHeaders
                FROM messages
                ORDER BY created_at DESC;
            
            """.trimIndent(),
            rowMapper
        )

    }

}
