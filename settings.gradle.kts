pluginManagement {

    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val dependencyManagementVersion: String by settings

    plugins {

        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion

        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version dependencyManagementVersion

    }

}

rootProject.name = "trakt"

include("core", "api-model", "api", "web")
