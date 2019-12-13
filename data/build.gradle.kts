plugins {

    kotlin("jvm")

    `java-test-fixtures`

}

java.sourceCompatibility = JavaVersion.VERSION_1_8

dependencies {

    implementation(project(":core"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.zaxxer:HikariCP")
    implementation("org.flywaydb:flyway-core")
    implementation("org.springframework.boot:spring-boot-starter")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation(testFixtures(project(":core")))
    testImplementation("io.mockk:mockk")
    testImplementation("org.junit.jupiter:junit-jupiter")

    testFixturesImplementation(kotlin("stdlib-jdk8"))
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

}
