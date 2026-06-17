plugins {
    kotlin("jvm") version "1.9.0"   // плагин Kotlin
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    // Твои зависимости
    implementation("org.apache.commons:commons-lang3:3.14.0")
    implementation("ch.qos.logback:logback-classic:1.5.6")
    implementation("org.slf4j:slf4j-api:2.0.13")

    testImplementation(kotlin("test"))
}

application {
    mainClass.set("org.example.MainKt")   // Важно! Для Kotlin - MainKt
}

tasks.test {
    useJUnitPlatform()
}

// Настройка цели JVM (как в методичке)
tasks.compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
}