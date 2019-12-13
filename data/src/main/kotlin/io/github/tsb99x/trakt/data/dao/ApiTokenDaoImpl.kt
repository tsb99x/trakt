package io.github.tsb99x.trakt.data.dao

import io.github.tsb99x.trakt.core.entity.ApiTokenEntity
import io.github.tsb99x.trakt.core.minusMinutes
import io.github.tsb99x.trakt.core.toUTC
import io.github.tsb99x.trakt.data.executeQuery
import io.github.tsb99x.trakt.data.executeUpdate
import io.github.tsb99x.trakt.data.getInstant
import io.github.tsb99x.trakt.data.getUUID
import java.sql.ResultSet
import java.time.Instant
import java.util.*
import javax.sql.DataSource

class ApiTokenDaoImpl(
    private val dataSource: DataSource,
    private val tokenLifetimeInMinutes: Long
) : ApiTokenDao {

    private val tableName = "api_tokens"
    private val rowHeaders = "id, user_id, created_at, last_used_at"
    private val rowArity = rowHeaders.split(",").joinToString(",") { "?" }

    private val rowMapper: (ResultSet) -> ApiTokenEntity = { rs ->

        ApiTokenEntity(
            id = rs.getUUID("id"),
            userId = rs.getUUID("user_id"),
            createdAt = rs.getInstant("created_at"),
            lastUsedAt = rs.getInstant("last_used_at")
        )

    }

    override fun insert(
        apiToken: ApiTokenEntity
    ) {

        dataSource.executeUpdate(
            """
                
                INSERT INTO $tableName ($rowHeaders)
                VALUES ($rowArity)
                
            """.trimIndent(),
            arrayOf(apiToken.id, apiToken.userId, apiToken.createdAt.toUTC(), apiToken.lastUsedAt.toUTC())
        )

    }

    override fun selectOneById(
        id: UUID
    ): ApiTokenEntity? {

        val expirationHorizon = Instant.now().minusMinutes(tokenLifetimeInMinutes)

        return dataSource.executeQuery(
            """

                SELECT $rowHeaders
                FROM $tableName
                WHERE id = ?
                AND last_used_at >= ?
                LIMIT 1
                
            """.trimIndent(),
            arrayOf(id, expirationHorizon.toUTC()),
            rowMapper
        ).firstOrNull()

    }

    override fun deleteById(
        id: UUID
    ) {

        dataSource.executeUpdate(
            """

                DELETE FROM $tableName
                WHERE id = ?
                
            """.trimIndent(),
            arrayOf(id)
        )

    }

    override fun updateLastUsedAtById(
        id: UUID
    ) {

        dataSource.executeUpdate(
            """
                
                UPDATE $tableName
                SET last_used_at = ?
                WHERE id = ?
                
            """.trimIndent(),
            arrayOf(Instant.now().toUTC(), id)
        )

    }

}
