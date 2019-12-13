package io.github.tsb99x.trakt.data.dao

import io.github.tsb99x.trakt.core.entity.MessageEntity
import io.github.tsb99x.trakt.core.toUTC
import io.github.tsb99x.trakt.data.executeQuery
import io.github.tsb99x.trakt.data.executeUpdate
import io.github.tsb99x.trakt.data.getInstant
import io.github.tsb99x.trakt.data.getUUID
import java.sql.ResultSet
import javax.sql.DataSource

class MessageDaoImpl(
    private val dataSource: DataSource
) : MessageDao {

    private val tableName = "messages"
    private val rowHeaders = "id, text, created_at"
    private val rowArity = rowHeaders.split(",").joinToString(",") { "?" }

    private val rowMapper: (ResultSet) -> MessageEntity = { rs ->

        MessageEntity(
            id = rs.getUUID("id"),
            text = rs.getString("text"),
            createdAt = rs.getInstant("created_at")
        )

    }

    override fun insert(
        entity: MessageEntity
    ) {

        dataSource.executeUpdate(
            """
            
                INSERT INTO $tableName ($rowHeaders)
                VALUES ($rowArity)
            
            """.trimIndent(),
            arrayOf(entity.id, entity.text, entity.createdAt.toUTC())
        )

    }

    override fun selectAllOrderByCreationTimeDesc(): List<MessageEntity> {

        return dataSource.executeQuery(
            """
            
                SELECT $rowHeaders
                FROM $tableName
                ORDER BY created_at DESC
            
            """.trimIndent(),
            emptyArray(),
            rowMapper
        )

    }

}
