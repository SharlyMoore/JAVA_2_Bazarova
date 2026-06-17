import java.util.Properties
import java.io.File
import java.io.FileOutputStream
import java.util.Date

plugins {
    kotlin("jvm") version "2.0.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.apache.commons:commons-lang3:3.14.0")
    implementation("ch.qos.logback:logback-classic:1.5.6")
    implementation("org.slf4j:slf4j-api:2.0.13")

    testImplementation(kotlin("test"))
}

application {
    mainClass.set("org.example.MainKt")
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}

abstract class PrintInfoTask : DefaultTask() {
    @TaskAction
    fun print() {
        println("=============================")
        println("Это моя первая пользовательская задача!")
        println("Проект: ${project.name}")
        println("Версия Gradle: ${project.gradle.gradleVersion}")
        println("=============================")
    }
}

tasks.register<PrintInfoTask>("printInfo") {
    group = "Custom"
    description = "Выводит информацию о проекте"
}

abstract class GenerateBuildInfoTask : DefaultTask() {
    @TaskAction
    fun generate() {
        val props = Properties()

        val userName = System.getenv("USER") ?: System.getenv("USERNAME") ?: "unknown"
        props.setProperty("build.user", userName)

        props.setProperty("build.os", System.getProperty("os.name"))
        props.setProperty("build.java.version", System.getProperty("java.version"))
        props.setProperty("build.timestamp", Date().toString())
        props.setProperty("build.message", "Сборка проекта ${project.name} выполнена успешно!")

        val resourcesDir = project.projectDir.resolve("src/main/resources")
        resourcesDir.mkdirs()

        val file = File(resourcesDir, "build-passport.properties")
        val output = FileOutputStream(file)
        props.store(output, "Build Passport")
        output.close()

        println("✅ Сгенерирован файл: ${file.absolutePath}")
    }
}

tasks.register<GenerateBuildInfoTask>("generateBuildInfo") {
    group = "build"
    description = "Генерирует файл build-passport.properties"
}

tasks.named("processResources") {
    dependsOn(tasks.named("generateBuildInfo"))
}

tasks.withType<JavaExec> {
    standardInput = System.`in`
}

// Fat JAR задача (работает без плагина shadow)
tasks.register<Jar>("shadowJar") {
    archiveBaseName.set(project.name)
    archiveClassifier.set("all")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes("Main-Class" to "org.example.MainKt")
    }

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}