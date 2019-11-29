plugins {

    kotlin("jvm")
    kotlin("plugin.spring")

    id("org.springframework.boot")

}

java.sourceCompatibility = JavaVersion.VERSION_1_8

dependencies {

    implementation(project(":core"))

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.session:spring-session-jdbc")

    testImplementation(testFixtures(project(":core")))

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin")

}
