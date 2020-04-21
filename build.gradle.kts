@file:Suppress("UNUSED_VARIABLE")

import com.github.lamba92.gradle.utils.TRAVIS_TAG
import com.github.lamba92.gradle.utils.implementation
import com.github.lamba92.gradle.utils.prepareForPublication

buildscript {
    repositories {
        maven("https://dl.bintray.com/lamba92/com.github.lamba92")
        google()
    }
    dependencies {
        classpath("com.github.lamba92", "lamba-gradle-utils", "1.0.6")
    }
}

plugins {
    kotlin("multiplatform") version "1.4-M1"
    id("com.jfrog.bintray") version "1.8.4"
    `maven-publish`
}

group = "com.github.lamba92"
version = TRAVIS_TAG ?: "0.0.1"

repositories {
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven("https://kotlin.bintray.com/kotlinx")
    jcenter()
    mavenCentral()
}

kotlin {

    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core-common", "1.3.5-1.4-M1")
                implementation("org.kodein.di", "kodein-di-core", "6.5.4")
                implementation("org.kodein.di", "kodein-di-erased-jvm", "6.5.4")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("reflect"))
                implementation("org.telegram", "telegrambots", "4.6")
                implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.3.5-1.4-M1")
            }
        }
    }
}

prepareForPublication()
