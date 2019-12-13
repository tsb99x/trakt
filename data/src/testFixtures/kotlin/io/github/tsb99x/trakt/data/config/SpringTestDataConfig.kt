package io.github.tsb99x.trakt.data.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@TestConfiguration
class SpringTestDataConfig {

    @Bean
    @Primary
    fun testDataConfig() =
        initTestDataConfig()

}
