package io.github.tsb99x.trakt

import io.github.tsb99x.trakt.data.MessageEntity
import io.github.tsb99x.trakt.data.UserEntity
import org.springframework.jdbc.core.JdbcTemplate
import java.time.LocalDateTime
import java.util.*

val adminUser = UserEntity(
    "5007f5be-bef7-428e-b40d-534d27c92f22".toUUID(),
    "admin",
    "\$2a\$11\$HXUtm.6N8OmQ9biKeW.vpOwmW5uoEG5GKa4J49e1RCM0RKHvnb23i",
    true
)

fun dumbMessageEntity(msg: String) =
    MessageEntity(UUID.randomUUID(), msg, LocalDateTime.now())

fun JdbcTemplate.truncate(tableName: String) =
    this.execute("TRUNCATE TABLE $tableName")