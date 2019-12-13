plugins {

    kotlin("jvm")

    `java-test-fixtures`

}

java.sourceCompatibility = JavaVersion.VERSION_1_8

dependencies {

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.slf4j:slf4j-api")
    implementation("org.mindrot:jbcrypt")

    testImplementation("io.mockk:mockk")
    testImplementation("org.junit.jupiter:junit-jupiter")

    testFixturesImplementation(kotlin("stdlib-jdk8"))

}
