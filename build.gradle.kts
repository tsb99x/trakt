import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {

    kotlin("jvm") apply false
    id("org.springframework.boot") apply false

    id("io.spring.dependency-management")

}

val springBootVersion: String by project
val postgresVersion: String by project
val mockitoKotlinVersion: String by project

subprojects {

    apply(plugin = "io.spring.dependency-management")

    group = "io.github.tsb99x"
    version = "0.0.1-SNAPSHOT"

    repositories {

        mavenCentral()

    }

    dependencyManagement {

        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
        }

        dependencies {
            dependency("com.nhaarman.mockitokotlin2:mockito-kotlin:$mockitoKotlinVersion")
            dependency("org.postgresql:postgresql:$postgresVersion")
        }

    }

    tasks.withType<KotlinCompile> {

        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }

    }

    tasks.withType<BootRun> {

        args("--spring.profiles.active=dev")

    }

    tasks.withType<Test> {

        useJUnitPlatform {
            excludeTags("integration")
        }

        testLogging {
            events("passed", "skipped", "failed")
        }

    }

    tasks.create<Test>("integrationTest") {

        useJUnitPlatform {
            includeTags("integration")
        }

    }

}
