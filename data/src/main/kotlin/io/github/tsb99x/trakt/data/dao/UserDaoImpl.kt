package io.github.tsb99x.trakt.data.dao

import io.github.tsb99x.trakt.core.entity.UserEntity
import io.github.tsb99x.trakt.data.executeQuery
import io.github.tsb99x.trakt.data.getUUID
import java.sql.ResultSet
import java.util.*
import javax.sql.DataSource

class UserDaoImpl(
    private val dataSource: DataSource
) : UserDao {

    private val tableName = "users"
    private val rowHeaders = "id, name, hash, enabled"

    private val rowMapper: (ResultSet) -> UserEntity = { rs ->

        UserEntity(
            id = rs.getUUID("id"),
            name = rs.getString("name"),
            hash = rs.getString("hash"),
            enabled = rs.getBoolean("enabled")
        )

    }

    override fun selectOneByUsername(
        username: String
    ): UserEntity? {

        return dataSource.executeQuery(
            """

                SELECT $rowHeaders
                FROM $tableName
                WHERE name = ?
                LIMIT 1
            
            """.trimIndent(),
            arrayOf(username),
            rowMapper
        ).firstOrNull()

    }

    override fun selectOneById(
        id: UUID
    ): UserEntity? {

        return dataSource.executeQuery(
            """
                
                SELECT $rowHeaders
                FROM $tableName
                WHERE id = ?
                
            """.trimIndent(),
            arrayOf(id),
            rowMapper
        ).firstOrNull()

    }

}
