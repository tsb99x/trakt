package io.github.tsb99x.trakt.core.service

import io.github.tsb99x.trakt.core.entity.MessageEntity
import io.github.tsb99x.trakt.core.exception.GenericException
import io.github.tsb99x.trakt.data.dao.MessageDao
import java.time.Instant
import java.util.*

open class MessageService(
    private val dao: MessageDao
) {

    fun add(
        msg: String
    ) {

        if (msg.isEmpty()) {
            throw RuntimeException("msg must not be empty")
        }

        if (msg == "123") {
            throw GenericException("msg must not be 123")
        }

        val entity = MessageEntity(UUID.randomUUID(), msg, Instant.now())
        dao.insert(entity)

    }

    fun getAll(): List<MessageEntity> {

        return dao.selectAllOrderByCreationTimeDesc()

    }

}
