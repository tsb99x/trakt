package io.github.tsb99x.trakt.core.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class PasswordEncoderConfig {

    @Bean
    fun passwordEncoder() =
        BCryptPasswordEncoder()

}
