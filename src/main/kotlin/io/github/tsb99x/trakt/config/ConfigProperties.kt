package io.github.tsb99x.trakt.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "trakt")
class ConfigProperties {

    /**
     * Configuration variables
     */

    var csrfEnabled: Boolean = true

    /**
     * Environment variables
     */

    var version: String = "development"

}
