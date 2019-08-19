package io.github.tsb99x.trakt.message

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class MessageDao(

    val jdbcTemplate: JdbcTemplate

) {

    fun create(entity: MessageEntity) {

        jdbcTemplate.update( // language=SQL
            """
            
                INSERT INTO messages (id, text)
                VALUES (?, ?);
            
            """.trimIndent(), entity.id, entity.text
        )

    }

    fun findAllByOrderByIdDesc(): List<MessageEntity> {

        return jdbcTemplate.query( // language=SQL
            """
            
                SELECT id, text
                FROM messages
                ORDER BY id DESC;
            
            """.trimIndent()
        ) { rs, _ ->

            MessageEntity(
                id = rs.getLong("id"),
                text = rs.getString("text")
            )

        }

    }

}
