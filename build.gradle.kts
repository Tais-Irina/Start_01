plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter:6.0.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:6.0.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:6.0.0")
    // Source: https://mvnrepository.com/artifact/org.assertj/assertj-core
    testImplementation("org.assertj:assertj-core:3.27.7")
    // Source: https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation("org.apache.commons:commons-lang3:3.20.0")
// Source: https://mvnrepository.com/artifact/com.google.guava/guava
    implementation("com.google.guava:guava:33.7.2-android")
    // Source: https://mvnrepository.com/artifact/org.assertj/assertj-core
    testImplementation("org.assertj:assertj-core:3.27.7")
    // Source: https://mvnrepository.com/artifact/org.awaitility/awaitility
    testImplementation("org.awaitility:awaitility:4.3.0")
    // Source: https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    implementation("com.fasterxml.jackson.core:jackson-databind:2.22.3")
    // Source: https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java
    implementation("org.seleniumhq.selenium:selenium-java:4.51.0")
    // Source: https://mvnrepository.com/artifact/com.codeborne/selenide
    implementation("com.codeborne:selenide:7.18.1")
    // Source: https://mvnrepository.com/artifact/io.rest-assured/rest-assured
    implementation("io.rest-assured:rest-assured:6.2.0")
}


//Задача запускает все тесты
tasks.test {
    useJUnitPlatform()
}

//Задача 1. запускает три метода из тестового класса под тегом Smoke
tasks.register<Test>("smokeTest"){
    description = "Run Smoke Tests"
    group="Test"
    useJUnitPlatform{
        includeTags("Smoke")
    }
}

//Задача 2. Запускает Задачу №1 и после прогонов пишет в консоль "Test run is over"
tasks.register("printOver"){
    description = "Prints Test run is over"
    group="Test"
    doLast {
        println("Test run is over")
    }
}

tasks.named("printOver")
{
    dependsOn("clean")
    dependsOn("smokeTest")
}


//Задача запускает APITest //APIGoods
tasks.register<Test>("taskAPIGoodsTest"){
    description = "Run API goods Tests"
    group="Test"
    useJUnitPlatform{
        includeTags("APIGoods")
    }
}





