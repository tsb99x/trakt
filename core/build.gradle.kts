plugins {

    kotlin("jvm")
    kotlin("plugin.spring")

    `java-test-fixtures`

}

java.sourceCompatibility = JavaVersion.VERSION_1_8

dependencies {

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("org.flywaydb:flyway-core")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.security:spring-security-crypto")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin")

    testFixturesImplementation(kotlin("stdlib-jdk8"))
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-jdbc")

}
