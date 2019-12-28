package io.github.tsb99x.trakt.config

import io.github.tsb99x.trakt.core.service.AuthorizationService
import io.github.tsb99x.trakt.core.service.MessageService
import io.github.tsb99x.trakt.data.dao.ApiTokenDao
import io.github.tsb99x.trakt.data.dao.MessageDao
import io.github.tsb99x.trakt.data.dao.UserDao

class CoreConfig(
    apiTokenDao: ApiTokenDao,
    userDao: UserDao,
    messageDao: MessageDao
) {

    val authorizationService =
        AuthorizationService(apiTokenDao, userDao)

    val messageService =
        MessageService(messageDao)

}
