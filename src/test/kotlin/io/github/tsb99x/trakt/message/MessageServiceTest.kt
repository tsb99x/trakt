package io.github.tsb99x.trakt.message

import io.github.tsb99x.trakt.dumbMessageEntity
import io.github.tsb99x.trakt.mock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class MessageServiceTest {

    private val messageDao: MessageDao = mock()
    private val messageService = MessageService(messageDao)

    @BeforeEach
    fun beforeEach() {

        reset(messageDao)

    }

    @Test
    fun `expect service to just add entity`() {

        val entity = dumbMessageEntity("random")
        messageService.add(entity)

        verify(messageDao).create(entity)
        verifyNoMoreInteractions(messageDao)

    }

    @Test
    fun `expect service to return all entities`() {

        val entities = listOf(
            dumbMessageEntity("a"),
            dumbMessageEntity("b"),
            dumbMessageEntity("c")
        )

        `when`(messageDao.findAllOrderByCreationTimeDesc())
            .thenReturn(entities)

        assertEquals(
            entities,
            messageService.getAll()
        )

    }

}
