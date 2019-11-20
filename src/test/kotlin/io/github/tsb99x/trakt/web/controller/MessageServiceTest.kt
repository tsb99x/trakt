package io.github.tsb99x.trakt.web.controller

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.github.tsb99x.trakt.data.MessageDao
import io.github.tsb99x.trakt.data.MessageEntity
import io.github.tsb99x.trakt.dumbMessageEntity
import io.github.tsb99x.trakt.service.MessageService
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

        val text = "random"
        messageService.add(text)

        val captor = argumentCaptor<MessageEntity>()
        verify(messageDao).insert(captor.capture())
        verifyNoMoreInteractions(messageDao)

        val entity = captor.firstValue
        assertEquals(text, entity.text)

    }

    @Test
    fun `expect service to return all entities`() {

        val entities = listOf(
            dumbMessageEntity("a"),
            dumbMessageEntity("b"),
            dumbMessageEntity("c")
        )

        doReturn(entities).whenever(messageDao).selectAllOrderByCreationTimeDesc()

        assertEquals(
            entities,
            messageService.getAll()
        )

    }

}
