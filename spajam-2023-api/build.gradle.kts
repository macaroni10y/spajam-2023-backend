import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.serialization") version "1.9.0"
    id("com.bmuschko.docker-spring-boot-application") version "9.0.1"
    id("com.palantir.git-version") version "3.0.0"
}

group = "jp.furyu"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("software.amazon.awssdk:dynamodb:2.20.65")
    implementation("software.amazon.awssdk:auth:2.20.65")
    implementation("com.aallam.openai:openai-client:3.3.0")
    implementation("io.ktor:ktor-client-apache5:2.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("com.aallam.ulid:ulid-kotlin:1.2.1")
    implementation("org.slf4j:slf4j-api:2.0.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

docker {
    // @see https://bmuschko.github.io/gradle-docker-plugin/current/user-guide/#spring-boot-plugin-extension

    val imageName = "spajam-2023-api"

    springBootApplication {
        baseImage.set("--platform=linux/amd64 openjdk:17-jdk-slim-buster")
        ports.set(listOf(8080))
        val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
        val details = versionDetails()
        val version = details.gitHashFull

        images.set(listOf("${imageName}:${version}", "${imageName}:latest", "417866577833.dkr.ecr.ap-northeast-1.amazonaws.com/${imageName}:latest"))
        mainClassName.set("jp.furyu.spajam2023api.Spajam2023ApiApplication")
    }
}
