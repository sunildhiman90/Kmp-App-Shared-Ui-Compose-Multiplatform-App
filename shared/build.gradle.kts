import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKmpLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version libs.versions.kotlin
    id("app.cash.sqldelight") version libs.versions.sqlite.driver
}

kotlin {
    jvm()

    js(IR) {
        browser()
    }

    android {
        namespace = "com.codingambitions.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
            freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
        }

        androidResources { enable = true }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true

            export(libs.com.arkivanov.decompose.decompose)
            export(libs.essenty.lifecycle)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            api(libs.image.loader)

            implementation(libs.com.arkivanov.decompose.decompose)
            implementation(libs.decompose.extensions.compose)

            implementation(libs.koin.core)

            implementation(libs.material3.windowsizeclass.multiplatform)
        }

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.com.arkivanov.decompose.decompose)
            implementation(libs.decompose.extensions.compose)
            implementation(libs.ktor.client.android)
            implementation(libs.koin.android)
            implementation(libs.android.driver)
        }

        jvmMain.dependencies {
            implementation(libs.sqlite.driver)
        }

        jsMain.dependencies {
            implementation(libs.web.worker.driver)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            api(libs.com.arkivanov.decompose.decompose)
            api(libs.essenty.lifecycle)
            implementation(libs.native.driver)
        }
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.codingambitions.kmpapp2")
            generateAsync.set(true)
        }
    }
}
