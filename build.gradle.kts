plugins {
    kotlin("jvm") version "1.3.61"
}

group = "com.github.lamba92"
version = "0.0.1"

repositories {
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    jcenter()
    mavenCentral()
}

dependencies {

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.3.2")

    api(kotlin("reflect"))
    implementation("org.telegram", "telegrambots", "4.4.0.1")
    implementation("org.kodein.di", "kodein-di-core-jvm", "6.4.1")
    implementation("org.kodein.di", "kodein-di-erased-jvm", "6.4.1")

}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}