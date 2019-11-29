plugins {

    kotlin("jvm")
    kotlin("plugin.spring")

    id("org.springframework.boot")

}

java.sourceCompatibility = JavaVersion.VERSION_1_8

dependencies {

    implementation(project(":core"))
    implementation(project(":api-model"))

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation(testFixtures(project(":core")))

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin")

}
