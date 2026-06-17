import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.text.*
import java.util.*

tasks.withType<JavaExec> {
    systemProperty("file.encoding", "UTF-8")
}

plugins {
    id("java")
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "org.example.Main"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("ch.qos.logback:logback-classic:1.5.32")
    implementation("org.apache.commons:commons-lang3:3.20.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    manifest {
        attributes(Pair("Main-Class", "org.example.Main"))
    }
}

abstract class PrintInfoTask : DefaultTask() { 
 
    @TaskAction  
    fun print() { 
        println("Тут находится пользовательская задача") 
        println("Проект: ${project.name}") 
        println("Версия Gradle: ${project.gradle.gradleVersion}") 
    } 
}

tasks.register<PrintInfoTask>("printInfo") { 
    group = "Custom" 
    description = "Выводит информацию о проекте"
}

abstract class GenerateBuildPassportTask : DefaultTask() {
    
    @TaskAction
    fun generate() {
        val userName = System.getenv("USERNAME") ?: System.getenv("USER") ?: "unknown"
        val osName = System.getProperty("os.name")
        val javaVersion = System.getProperty("java.version")
        val buildTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        val greeting = "Привет это Даша!"

        val content = """
            user=$userName
            os=$osName
            java=$javaVersion
            build.time=$buildTime
            greeting=$greeting
        """.trimIndent()

        val resourcesDir = project.projectDir.resolve("src/main/resources")
        resourcesDir.mkdirs()
        val passportFile = resourcesDir.resolve("build-passport.properties")
        passportFile.writeText(content)
        
        println("Build passport generated at ${passportFile.absolutePath}")
        println("Собрано пользователем: $userName")
        println("Время сборки: $buildTime")
    }
}

tasks.register<GenerateBuildPassportTask>("generateBuildPassport") {
    group = "build"
    description = "Генерирует паспорт сборки"
}

tasks.named("processResources") {
    dependsOn("generateBuildPassport")
}