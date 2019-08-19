package io.github.tsb99x.trakt.message

import org.springframework.stereotype.Service

@Service
class MessageService(

    val dao: MessageDao

) {

    fun add(entity: MessageEntity) {

        dao.create(entity)

    }

    fun getAll(): List<MessageEntity> {

        return dao.findAllByOrderByIdDesc()

    }

}
