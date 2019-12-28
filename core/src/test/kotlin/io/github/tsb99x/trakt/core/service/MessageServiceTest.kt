package io.github.tsb99x.trakt.core.service

import io.github.tsb99x.trakt.core.dumbMessageEntity
import io.github.tsb99x.trakt.data.dao.MessageDao
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MessageServiceTest {

    private val messageDao: MessageDao = mockk()
    private val messageService = MessageService(messageDao)

    @Test
    fun `expect service to just add entity`() {

        every { messageDao.insert(any()) } just runs

        val text = "random"
        messageService.add(text)

        verify { messageDao.insert(withArg { assertEquals(text, it.text) }) }

    }

    @Test
    fun `expect service to return all entities`() {

        val entities = listOf(
            dumbMessageEntity("a"),
            dumbMessageEntity("b"),
            dumbMessageEntity("c")
        )

        every { messageDao.selectAllOrderByCreationTimeDesc() } returns entities

        assertEquals(entities, messageService.getAll())

    }

}
