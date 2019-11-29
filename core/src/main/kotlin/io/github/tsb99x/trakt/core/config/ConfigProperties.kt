package io.github.tsb99x.trakt.core.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "trakt")
class ConfigProperties {

    /**
     * Configuration variables
     */

    var csrfEnabled: Boolean = true
    var tokenLifetimeInMinutes: Long = 7 /* days */ * 24 /* hours */ * 60 /* minutes */

    /**
     * Environment variables
     */

    var version: String = "development"

}
