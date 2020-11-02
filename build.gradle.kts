import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

    application
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")

}

group = "io.github.tsb99x"
version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_1_8

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

tasks {

    application {
        mainClassName = "io.github.tsb99x.trakt.TraktApplicationKt"
        applicationDefaultJvmArgs = listOf(
                "-Ddatasource.url=jdbc:postgresql://localhost:49432/trakt",
                "-Ddatasource.username=trakt",
                "-Ddatasource.password=trakt"
        )
    }

    jar {
        manifest {
            attributes["Main-Class"] = application.mainClassName
        }
    }

}

val jacksonVersion: String by project
val jettyVersion: String by project
val junitVersion: String by project
val logbackVersion: String by project
val mockkVersion: String by project
val flywayVersion: String by project
val hikariVersion: String by project
val postgresVersion: String by project
val slf4jVersion: String by project
val jbcryptVersion: String by project

dependencies {

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation("org.mindrot:jbcrypt:$jbcryptVersion")

    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    implementation("org.eclipse.jetty:jetty-server:$jettyVersion")
    implementation("org.eclipse.jetty:jetty-servlet:$jettyVersion")
    runtimeOnly("org.eclipse.jetty:jetty-io:$jettyVersion")

    implementation("com.zaxxer:HikariCP:$hikariVersion")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    runtimeOnly("org.postgresql:postgresql:$postgresVersion")

    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

}
