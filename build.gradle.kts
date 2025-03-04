plugins {
    id("java")
    id("io.qameta.allure") version "2.12.0"
    id ("jacoco")
}

group = "com.example"
version = "1.0-SNAPSHOT"

jacoco {
    toolVersion = "0.8.8"
}

repositories {
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("io.qameta.allure:allure-junit5:2.20.1")

    implementation("ch.qos.logback:logback-classic:1.4.12")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy (tasks.jacocoTestReport)
}

allure {
    version = "2.30.0"

}

tasks.jacocoTestReport {
    dependsOn (tasks.test) // Задача по отчету зависит от тестов
            reports {
                xml.required = true
                html.required = true
                html.outputLocation = file("$buildDir/reports/jacoco") // Укажите путь для отчета
            }
}

tasks.named("allureReport") {
    group = "verification"
    description = "Generate Allure report"
}