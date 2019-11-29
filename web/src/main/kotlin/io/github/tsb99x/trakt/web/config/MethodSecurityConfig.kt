package io.github.tsb99x.trakt.web.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration

@Configuration
@EnableGlobalMethodSecurity(
    securedEnabled = true
)
class MethodSecurityConfig : GlobalMethodSecurityConfiguration()
