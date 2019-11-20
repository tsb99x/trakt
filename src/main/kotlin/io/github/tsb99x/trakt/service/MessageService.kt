package io.github.tsb99x.trakt.service

import io.github.tsb99x.trakt.data.MessageDao
import io.github.tsb99x.trakt.data.MessageEntity
import io.github.tsb99x.trakt.exception.GenericException
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class MessageService(
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