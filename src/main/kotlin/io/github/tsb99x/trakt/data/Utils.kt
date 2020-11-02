package io.github.tsb99x.trakt.data

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.time.Instant
import java.time.OffsetDateTime
import java.util.*
import javax.sql.DataSource

fun ResultSet.getUUID(columnLabel: String): UUID =
    this.getObject(columnLabel, UUID::class.java)

fun ResultSet.getInstant(columnLabel: String): Instant =
    this.getObject(columnLabel, OffsetDateTime::class.java).toInstant()

inline fun <T> DataSource.forPreparedStatement(
    sql: String,
    params: Array<out Any>,
    fn: (PreparedStatement) -> T
): T =
    this.connection.use { conn ->
        conn.prepareStatement(sql).use { stmt ->

            params.forEachIndexed { index, param ->
                stmt.setObject(index + 1, param)
            }

            fn(stmt)

        }
    }

fun DataSource.executeUpdate(
    sql: String,
    params: Array<out Any> = emptyArray()
): Int =
    forPreparedStatement(sql, params) {
        it.executeUpdate()
    }

fun <T> DataSource.executeQuery(
    sql: String,
    params: Array<out Any>,
    rowMapper: (ResultSet) -> T
): List<T> =
    forPreparedStatement(sql, params) {
        it.executeQuery().use { rs ->

            val res = mutableListOf<T>()

            while (rs.next()) {
                res += rowMapper(rs)
            }

            res

        }
    }
