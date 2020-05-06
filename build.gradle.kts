@file:Suppress("UNUSED_VARIABLE", "SuspiciousCollectionReassignment")

import com.github.lamba92.gradle.utils.*

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

    metadata {
        compilations.all {
            kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
        }
    }

    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
            }
        }
    }

    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(kotlinx("coroutines-core-common", "1.3.5-1.4-M1"))
                api(kodein("core", "6.5.5"))
                api(kodein("erased-jvm", "6.5.5"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("reflect"))
                api("org.telegram", "telegrambots", "4.6")
                api(kotlinx("coroutines-core", "1.3.5-1.4-M1"))
            }
        }
    }
}

prepareForPublication()
