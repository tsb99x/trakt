pluginManagement {

    val kotlinVersion: String by settings
    val shadowVersion: String by settings

    plugins {

        kotlin("jvm") version kotlinVersion
        id("com.github.johnrengelman.shadow") version shadowVersion

    }

}

rootProject.name = "trakt"

include("core", "data", "api-model", "api", "web")
