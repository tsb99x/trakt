package io.github.tsb99x.trakt.data.config

import io.github.tsb99x.trakt.core.service.AuthorizationService
import io.github.tsb99x.trakt.core.service.MessageService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
open class SpringDataConfig {

    @Bean
    @Profile("prod")
    open fun dataConfig() =
        initDataConfig()

    @Bean
    open fun authorizationService(dataConfig: DataConfig) =
        AuthorizationService(dataConfig.apiTokenDao, dataConfig.userDao)

    @Bean
    open fun messageService(dataConfig: DataConfig) =
        MessageService(dataConfig.messageDao)

}
