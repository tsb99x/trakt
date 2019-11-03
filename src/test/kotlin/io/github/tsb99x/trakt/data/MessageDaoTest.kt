package io.github.tsb99x.trakt.data

import io.github.tsb99x.trakt.truncate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

@SpringBootTest
@ActiveProfiles("dev")
@Tag("integration")
class MessageDaoTest @Autowired constructor(
    private val messageDao: MessageDao,
    private val jdbcTemplate: JdbcTemplate
) {

    @BeforeEach
    fun beforeEach() {

        jdbcTemplate.truncate("messages")

    }

    @Test
    fun `expect insert and find with creation time ordering to work`() {

        val dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        val entity = MessageEntity(UUID.randomUUID(), "random", dateTime)
        messageDao.insert(entity)

        assertEquals(
            listOf(entity),
            messageDao.findAllOrderByCreationTimeDesc()
        )

        val newDateTime = dateTime.plusMinutes(1)
        val newEntity = MessageEntity(UUID.randomUUID(), "non-random", newDateTime)
        messageDao.insert(newEntity)

        assertEquals(
            listOf(newEntity, entity),
            messageDao.findAllOrderByCreationTimeDesc()
        )

    }

}
