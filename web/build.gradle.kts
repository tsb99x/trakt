plugins {

    application
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")

}

tasks {

    application {
        mainClassName = "io.github.tsb99x.trakt.TraktWebApplicationKt"
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

java.sourceCompatibility = JavaVersion.VERSION_1_8

val jacksonVersion: String by project
val jettyVersion: String by project
val logbackVersion: String by project
val mockkVersion: String by project
val thymeleafVersion: String by project

dependencies {

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation(project(":core"))
    implementation(project(":data"))

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation("org.eclipse.jetty:jetty-server:$jettyVersion")
    implementation("org.eclipse.jetty:jetty-servlet:$jettyVersion")
    runtimeOnly("org.eclipse.jetty:jetty-io:$jettyVersion")

    implementation("org.thymeleaf:thymeleaf:$thymeleafVersion")

    testImplementation(testFixtures(project(":core")))
    testImplementation(testFixtures(project(":data")))

    testImplementation("io.mockk:mockk:$mockkVersion")

}
