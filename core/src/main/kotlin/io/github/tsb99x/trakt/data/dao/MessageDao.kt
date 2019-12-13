package io.github.tsb99x.trakt.data.dao

import io.github.tsb99x.trakt.core.entity.MessageEntity

interface MessageDao {

    fun insert(
        entity: MessageEntity
    )

    fun selectAllOrderByCreationTimeDesc(): List<MessageEntity>

}
