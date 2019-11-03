import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {

    kotlin("jvm")
    kotlin("plugin.spring")

    id("org.springframework.boot")
    id("io.spring.dependency-management")

}

group = "io.github.tsb99x"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {

    mavenCentral()

}

val postgresVersion: String by project
val mockitoKotlinVersion: String by project

dependencies {

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.session:spring-session-jdbc")

    implementation("org.flywaydb:flyway-core")

    runtimeOnly("org.postgresql:postgresql:$postgresVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:$mockitoKotlinVersion")

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

tasks.withType<KotlinCompile> {

    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }

}
