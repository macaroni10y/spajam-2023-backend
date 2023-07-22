import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    id("com.bmuschko.docker-spring-boot-application") version "9.0.1"
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
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("software.amazon.awssdk:dynamodb:2.20.65")
    implementation("software.amazon.awssdk:auth:2.20.65")
    implementation("com.aallam.openai:openai-client:3.3.0")
    implementation("com.aallam.ulid:ulid-kotlin:1.2.1")
    implementation ("org.slf4j:slf4j-api:2.0.5")
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
        baseImage.set("openjdk:17-jdk-slim-buster")
        ports.set(listOf(8080))
        images.set(listOf("${imageName}:${version}", "${imageName}:latest"))
        mainClassName.set("jp.furyu.spajam2023api.Spajam2023ApiApplication")
    }
}
