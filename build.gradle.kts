plugins {
    java
    `maven-publish`
    kotlin("plugin.lombok") version "1.7.21"
    id("io.freefair.lombok") version "6.3.0"
}

group = "com.example"
version = "1.0-SNAPSHOT"
description = "robot-parser"

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
//    annotationProcessor("org.projectlombok:lombok")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.testng:testng:7.3.0")
}