@file:Suppress("UNUSED_VARIABLE")

import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask

plugins {
    kotlin("multiplatform") version "1.4-M1"
    id("com.jfrog.bintray") version "1.8.4"
    `maven-publish`
}

group = "com.github.lamba92"
version = `travis-tag` ?: "0.0.1"

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
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.5-1.4-M1")
                implementation("org.kodein.di:kodein-di-core:6.5.4")
                implementation("org.kodein.di:kodein-di-erased-jvm:6.5.4")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("reflect"))
                implementation("org.telegram:telegrambots:4.6")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5-1.4-M1")
            }
        }
    }
}

bintray {
    user = searchPropertyOrNull("bintrayUsername", "BINTRAY_USERNAME")
    key = searchPropertyOrNull("bintrayApiKey", "BINTRAY_API_KEY")
    pkg {
        version {
            name = project.version as String
        }
        repo = group as String
        name = rootProject.name
        setLicenses("Apache-2.0")
        vcsUrl = "https://github.com/lamba92/${rootProject.name}"
        issueTrackerUrl = "https://github.com/lamba92/${rootProject.name}/issues"
    }
    publish = true
    setPublications(publishing.publications.names)
}

tasks.withType<BintrayUploadTask> {
    doFirst {
        publishing.publications.withType<MavenPublication> {
            buildDir.resolve("publications/$name/module.json").let {
                if (it.exists())
                    artifact(object : org.gradle.api.publish.maven.internal.artifact.FileBasedMavenArtifact(it) {
                        override fun getDefaultExtension() = "module"
                    })
            }
        }
    }
}

fun BintrayExtension.pkg(action: BintrayExtension.PackageConfig.() -> Unit) {
    pkg(closureOf(action))
}

fun BintrayExtension.PackageConfig.version(action: BintrayExtension.VersionConfig.() -> Unit) {
    version(closureOf(action))
}

fun searchPropertyOrNull(name: String, vararg aliases: String): String? {

    fun searchEverywhere(name: String): String? =
        findProperty(name) as? String? ?: System.getenv(name)

    searchEverywhere(name)?.let { return it }

    with(aliases.iterator()) {
        while (hasNext()) {
            searchEverywhere(next())?.let { return it }
        }
    }

    return null
}

@Suppress("PropertyName")
val `travis-tag`
    get() = System.getenv("TRAVIS_TAG").run {
        if (isNullOrBlank()) null else this
    }

fun BintrayExtension.setPublications(names: Collection<String>) =
    setPublications(*names.toTypedArray())
