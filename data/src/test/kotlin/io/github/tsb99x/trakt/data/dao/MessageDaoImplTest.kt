package io.github.tsb99x.trakt.data.dao

import io.github.tsb99x.trakt.core.INTEGRATION
import io.github.tsb99x.trakt.core.entity.MessageEntity
import io.github.tsb99x.trakt.data.initTestDataConfig
import io.github.tsb99x.trakt.data.truncate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Tag(INTEGRATION)
class MessageDaoImplTest {

    private val config = initTestDataConfig()
    private val messageDao = config.messageDao

    @BeforeEach
    fun beforeEach() {

        config.dataSource.truncate("messages")

    }

    @Test
    fun `expect insert and select all order by creation time to work`() {

        val dateTime = Instant.now().truncatedTo(ChronoUnit.SECONDS)
        val entity = MessageEntity(UUID.randomUUID(), "random", dateTime)
        messageDao.insert(entity)

        assertEquals(
            listOf(entity),
            messageDao.selectAllOrderByCreationTimeDesc()
        )

        val newDateTime = dateTime.plusSeconds(30)
        val newEntity = MessageEntity(UUID.randomUUID(), "non-random", newDateTime)
        messageDao.insert(newEntity)

        assertEquals(
            listOf(newEntity, entity),
            messageDao.selectAllOrderByCreationTimeDesc()
        )

    }

}
