plugins {

    `java-test-fixtures`
    kotlin("jvm")

}

java.sourceCompatibility = JavaVersion.VERSION_1_8

val flywayVersion: String by project
val hikariVersion: String by project
val junitVersion: String by project
val mockkVersion: String by project
val postgresVersion: String by project

dependencies {

    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":core"))

    implementation("com.zaxxer:HikariCP:$hikariVersion")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    runtimeOnly("org.postgresql:postgresql:$postgresVersion")

    testFixturesImplementation(kotlin("stdlib-jdk8"))

    testImplementation(testFixtures(project(":core")))

    testImplementation("io.mockk:mockk:$mockkVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

}
