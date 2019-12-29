import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

    kotlin("jvm") apply false

}

subprojects {

    group = "io.github.tsb99x"
    version = "0.0.1-SNAPSHOT"

    repositories {

        mavenCentral()

    }

    tasks.withType<KotlinCompile> {

        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }

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
