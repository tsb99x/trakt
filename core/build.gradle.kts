plugins {

    `java-test-fixtures`
    kotlin("jvm")

}

java.sourceCompatibility = JavaVersion.VERSION_1_8

val jbcryptVersion: String by project
val junitVersion: String by project
val mockkVersion: String by project
val slf4jVersion: String by project

dependencies {

    implementation(kotlin("stdlib-jdk8"))

    implementation("org.mindrot:jbcrypt:$jbcryptVersion")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")

    testFixturesImplementation(kotlin("stdlib-jdk8"))

    testImplementation("io.mockk:mockk:$mockkVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

}
