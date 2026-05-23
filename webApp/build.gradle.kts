plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    js(IR) {
        outputModuleName = "KmpApp2"
        browser {
            commonWebpackConfig {
                outputFileName = "KmpApp2.js"
                devServer = (devServer
                    ?: org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.DevServer()).copy()
            }
        }
        binaries.executable()
    }

    sourceSets {
        jsMain.dependencies {
            implementation(projects.shared)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(libs.com.arkivanov.decompose.decompose)
            implementation(libs.decompose.extensions.compose)
            implementation(libs.koin.core)
            implementation(npm("@cashapp/sqldelight-sqljs-worker", libs.versions.sqlite.driver.get()))
            implementation(npm("sql.js", "1.8.0"))
            implementation(libs.web.worker.driver)
            implementation(devNpm("copy-webpack-plugin", "9.1.0"))
        }
    }
}
