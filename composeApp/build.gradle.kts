import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)

    kotlin("plugin.serialization") version libs.versions.kotlin //decompose step2

    id("app.cash.sqldelight") version libs.versions.sqlite.driver //sqldelight step1

    //for Kotlin 2.0
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
            freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
        }
//        compilations.all {
//            kotlinOptions {
//                jvmTarget = "1.8"
//            }
//        }
    }


    jvm("desktop")

    //Step1
    js(IR) {
        moduleName = "KmpApp2"
        browser() {

            //Tool bundler for converting kotlin code to js code
            commonWebpackConfig() {
                outputFileName = "KmpApp2.js"
                devServer = (devServer
                    ?: org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.DevServer()).copy()
            }
            binaries.executable() //it will generate executable js files
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true

            export(libs.com.arkivanov.decompose.decompose)
            export(libs.essenty.lifecycle)
        }
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)

            //decompose step3
            implementation(libs.com.arkivanov.decompose.decompose)
            implementation(libs.decompose.extensions.compose)

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(libs.mvvm.core)

            api(libs.image.loader)


            implementation(libs.com.arkivanov.decompose.decompose)
            implementation(libs.decompose.extensions.compose)
            //decompose step1

            //koin step1
            implementation(libs.koin.core)

            implementation(libs.material3.windowsizeclass.multiplatform)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)

            implementation(libs.sqlite.driver)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.android)

            //koin step2
            implementation(libs.koin.android)

            implementation(libs.android.driver)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)

            api(libs.com.arkivanov.decompose.decompose)
            api(libs.essenty.lifecycle)

            implementation(libs.native.driver)

        }

        jsMain.dependencies {


            implementation(npm("@cashapp/sqldelight-sqljs-worker", libs.versions.sqlite.driver.get()))
            implementation(npm("sql.js", "1.8.0"))
            implementation(libs.web.worker.driver)
            implementation(devNpm("copy-webpack-plugin", "9.1.0"))
        }
    }
}

android {
    namespace = "com.codingambitions.kmpapp2"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.codingambitions.kmpapp2"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.codingambitions.kmpapp2"
            packageVersion = "1.0.0"
        }
    }
}


//This is not needed in Compose Multiplatform 1.6.10
//Step3
//compose.experimental {
//    web.application {}
//}

//sqldelight step2
sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.codingambitions.kmpapp2")
            generateAsync.set(true)
        }
    }
}